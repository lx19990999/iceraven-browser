/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.components.toolbar

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import mozilla.components.browser.state.selector.normalTabs
import mozilla.components.browser.state.selector.privateTabs
import mozilla.components.browser.toolbar.BrowserToolbar
import mozilla.components.browser.toolbar.display.DisplayToolbar
import mozilla.components.concept.toolbar.ScrollableToolbar
import mozilla.components.concept.toolbar.Toolbar
import mozilla.components.feature.tabs.toolbar.TabCounterToolbarButton
import mozilla.components.feature.toolbar.ToolbarBehaviorController
import mozilla.components.feature.toolbar.ToolbarFeature
import mozilla.components.feature.toolbar.ToolbarPresenter
import mozilla.components.support.base.feature.LifecycleAwareFeature
import mozilla.components.support.ktx.android.content.getColorFromAttr
import mozilla.components.support.ktx.android.view.hideKeyboard
import mozilla.components.ui.tabcounter.TabCounterMenu
import mozilla.telemetry.glean.private.NoExtras
import org.mozilla.fenix.GleanMetrics.AddressToolbar
import org.mozilla.fenix.R
import org.mozilla.fenix.browser.tabstrip.isTabStripEnabled
import org.mozilla.fenix.components.menu.MenuAccessPoint
import org.mozilla.fenix.components.toolbar.interactor.BrowserToolbarInteractor
import org.mozilla.fenix.components.toolbar.ui.createShareBrowserAction
import org.mozilla.fenix.ext.components
import org.mozilla.fenix.ext.settings
import org.mozilla.fenix.theme.ThemeManager

/**
 * Feature configuring the toolbar when in display mode.
 */
@SuppressWarnings("LongParameterList")
abstract class ToolbarIntegration(
    private val context: Context,
    private val toolbar: BrowserToolbar,
    scrollableToolbar: ScrollableToolbar,
    toolbarMenu: ToolbarMenu,
    private val interactor: BrowserToolbarInteractor,
    private val customTabId: String?,
    isPrivate: Boolean,
    renderStyle: ToolbarFeature.RenderStyle,
) : LifecycleAwareFeature {

    val store = context.components.core.store
    private val toolbarPresenter: ToolbarPresenter = ToolbarPresenter(
        toolbar = toolbar,
        store = store,
        customTabId = customTabId,
        shouldDisplaySearchTerms = true,
        urlRenderConfiguration = ToolbarFeature.UrlRenderConfiguration(
            context.components.publicSuffixList,
            context.getColorFromAttr(R.attr.textPrimary),
            context.getColorFromAttr(R.attr.textSecondary),
            renderStyle = renderStyle,
        ),
    )

    private val menuPresenter =
        MenuPresenter(toolbar, context.components.core.store, customTabId)

    private val toolbarController = ToolbarBehaviorController(scrollableToolbar, store, customTabId)

    init {
        // 不设置 menuBuilder，这样就不会在右侧显示默认的菜单按钮
        // if (!context.settings().enableMenuRedesign) {
        //     toolbar.display.menuBuilder = toolbarMenu.menuBuilder
        // }

        toolbar.private = isPrivate

        // 强制禁用安全指示器
        toolbar.display.indicators = listOf(
            DisplayToolbar.Indicators.EMPTY,
            DisplayToolbar.Indicators.HIGHLIGHT,
        )

        // 不使用原来的菜单按钮添加逻辑
        // if (context.settings().enableMenuRedesign && customTabId == null) {
        //     addMenuBrowserAction()
        // }
    }

    override fun start() {
        menuPresenter.start()
        toolbarPresenter.start()
        toolbarController.start()
    }

    override fun stop() {
        menuPresenter.stop()
        toolbarPresenter.stop()
        toolbarController.stop()
    }

    fun invalidateMenu() {
        menuPresenter.invalidateActions()
    }

    private fun addMenuBrowserAction() {
        val menuAction = Toolbar.ActionButton(
            imageDrawable = AppCompatResources.getDrawable(
                context,
                R.drawable.mozac_ic_ellipsis_vertical_24,
            )!!,
            contentDescription = context.getString(R.string.content_description_menu),
            visible = {
                context.settings().enableMenuRedesign
            },
            weight = { 1 }, // 设置为最小权重，让菜单按钮显示在最左侧
            iconTintColorResource = ThemeManager.resolveAttribute(R.attr.textPrimary, context),
            listener = {
                val accessPoint = if (customTabId.isNullOrBlank()) {
                    MenuAccessPoint.Browser
                } else {
                    MenuAccessPoint.External
                }

                interactor.onMenuButtonClicked(accessPoint = accessPoint)
            },
        )

        // 将菜单按钮添加到左侧导航区域而不是右侧浏览器区域
        toolbar.addNavigationAction(menuAction)
    }
}

@SuppressWarnings("LongParameterList")
class DefaultToolbarIntegration(
    private val context: Context,
    private val toolbar: BrowserToolbar,
    scrollableToolbar: ScrollableToolbar,
    toolbarMenu: ToolbarMenu,
    private val lifecycleOwner: LifecycleOwner,
    private val customTabId: String? = null,
    private val isPrivate: Boolean,
    private val interactor: BrowserToolbarInteractor,
) : ToolbarIntegration(
    context = context,
    toolbar = toolbar,
    scrollableToolbar = scrollableToolbar,
    toolbarMenu = toolbarMenu,
    interactor = interactor,
    customTabId = customTabId,
    isPrivate = isPrivate,
    renderStyle = ToolbarFeature.RenderStyle.ColoredUrl,
) {

    @VisibleForTesting
    internal var cfrPresenter = BrowserToolbarCFRPresenter(
        context = context,
        browserStore = context.components.core.store,
        settings = context.settings(),
        toolbar = toolbar,
        isPrivate = isPrivate,
        customTabId = customTabId,
    )

    init {
        toolbar.display.indicators = listOf(
            // DisplayToolbar.Indicators.SECURITY, // 隐藏安全指示器（HTTPS锁图标）
            DisplayToolbar.Indicators.EMPTY,
            DisplayToolbar.Indicators.HIGHLIGHT,
        )

        // 强制添加菜单按钮到左侧，无论菜单重设计是否启用
        addMenuNavigationAction()

        if (context.isTabStripEnabled()) {
            addShareBrowserAction()
        } else {
            addNewTabBrowserAction()
            addTabCounterBrowserAction()
        }
    }

    private fun addNewTabBrowserAction() {
        val newTabAction = BrowserToolbar.Button(
            imageDrawable = AppCompatResources.getDrawable(context, R.drawable.mozac_ic_plus_24)!!,
            contentDescription = context.getString(R.string.library_new_tab),
            visible = { false },
            weight = { NEW_TAB_ACTION_WEIGHT },
            iconTintColorResource = ThemeManager.resolveAttribute(R.attr.textPrimary, context),
            listener = interactor::onNewTabButtonClicked,
        )

        toolbar.addBrowserAction(newTabAction)
    }

    private fun addTabCounterBrowserAction() {
        val tabCounterAction = TabCounterToolbarButton(
            lifecycleOwner = lifecycleOwner,
            showTabs = {
                toolbar.hideKeyboard()
                interactor.onTabCounterClicked()
            },
            store = store,
            menu = buildTabCounterMenu(),
            visible = { true },
            weight = { 2 }, // 设置为第二小的权重，让标签计数器显示在菜单按钮右侧
        )

        val tabCount = if (isPrivate) {
            store.state.privateTabs.size
        } else {
            store.state.normalTabs.size
        }

        tabCounterAction.updateCount(tabCount)

        // 将标签计数器也添加到左侧导航区域
        toolbar.addNavigationAction(tabCounterAction)
    }

    private fun addShareBrowserAction() {
        toolbar.addBrowserAction(
            BrowserToolbar.createShareBrowserAction(
                context = context,
                listener = {
                    AddressToolbar.shareTapped.record((NoExtras()))
                    interactor.onShareActionClicked()
                },
            ),
        )
    }

    override fun start() {
        super.start()
        cfrPresenter.start()
    }

    override fun stop() {
        cfrPresenter.stop()
        super.stop()
    }

    private fun addMenuNavigationAction() {
        val menuAction = BrowserToolbar.Button(
            imageDrawable = AppCompatResources.getDrawable(
                context,
                R.drawable.mozac_ic_ellipsis_vertical_24,
            )!!,
            contentDescription = context.getString(R.string.content_description_menu),
            iconTintColorResource = ThemeManager.resolveAttribute(R.attr.textPrimary, context),
            listener = {
                val accessPoint = if (customTabId.isNullOrBlank()) {
                    MenuAccessPoint.Browser
                } else {
                    MenuAccessPoint.External
                }
                interactor.onMenuButtonClicked(accessPoint = accessPoint)
            },
        )

        // 将菜单按钮添加到左侧导航区域
        toolbar.addNavigationAction(menuAction)
    }

    private fun buildTabCounterMenu(): TabCounterMenu =
        FenixTabCounterMenu(
            context = context,
            onItemTapped = {
                interactor.onTabCounterMenuItemTapped(it)
            },
            iconColor = if (isPrivate) {
                ContextCompat.getColor(context, R.color.fx_mobile_private_icon_color_primary)
            } else {
                null
            },
        ).also {
            it.updateMenu(context.settings().toolbarPosition)
        }

    companion object {
        private const val NEW_TAB_ACTION_WEIGHT = 3
        private const val TAB_COUNTER_ACTION_WEIGHT = 2
    }
}
