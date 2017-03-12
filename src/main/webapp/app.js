(function () {
    'use strict';

    angular.module('myApp', [
        'myApp.services',
        'ui.router',
        'chart.js','ngCookies'
    ]).config(['$stateProvider', '$urlRouterProvider', 'ChartJsProvider', function ($stateProvider, $urlRouterProvider, ChartJsProvider) {

        ChartJsProvider.setOptions({ colors : ['#803690', '#00ADF9', '#DCDCDC', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'] });

        $stateProvider.state('default', {
            url: '',
            views: {
                "body@": {
                    templateUrl: 'rms/core/body.html',
                    controller: 'BodyController',
                    controllerAs: 'vm'
                },
                "sidebar": {
                    templateUrl: 'rms/core/sidebar.html',
                    controller: 'SidebarController',
                    controllerAs: 'vm'
                }
            }
        }).state('login', {
            url: '/login',
            views: {
                "body@": {
                    templateUrl: 'rms/core/login.html',
                    controller: 'LoginController',
                    controllerAs: 'vm'
                }
            }
        }).state('register', {
            url: '/register',
            views: {
                "body@": {
                    templateUrl: 'rms/core/register.html',
                    controller: 'RegisterController',
                    controllerAs: 'vm'
                }
            }
        }).state('dashboard', {
            parent: 'default',
            url: "/dashboard",
            views: {
                "view@default": {
                    templateUrl: 'rms/dashboard/dashboard.html',
                    controller: 'DashboardController',
                    controllerAs: 'vm'
                }
            }
        }).state('settings', {
            parent: 'default',
            url: '/settings',
            views: {
                "view@default": {
                    templateUrl: 'rms/core/settings.html',
                    controller: 'SettingsController',
                    controllerAs: 'vm'
                }
            }
        }).state('menu', {
            parent: 'default',
            url: '/menu',
            views: {
                "view@default": {
                    templateUrl: 'rms/menu/menu-management/menu-management.html',
                    controller: 'MenuManagementController',
                    controllerAs: 'vm'
                }
            }
        }).state('order_management', {
            parent: 'default',
            url: '/order_management',
            views: {
                "view@default": {
                    templateUrl: 'rms/orders/order-management/order-management.html',
                    controller: 'OrderManagementController',
                    controllerAs: 'vm'
                }
            }
        }).state('order_update', {
            parent: 'default',
            url: '/order_update/:orderId',
            views: {
                "view@default": {
                    templateUrl: 'rms/orders/order-update/order-update.html',
                    controller: 'OrderUpdateController',
                    controllerAs: 'vm'
                }
            }
        }).state('orders_history', {
            parent: 'default',
            url: '/orders_history',
            views: {
                "view@default": {
                    templateUrl: 'rms/orders/orders-history/orders-history.html',
                    controller: 'OrdersHistoryController',
                    controllerAs: 'vm'
                }
            }
        }).state('pending_orders', {
            parent: 'default',
            url: '/pending_orders',
            views: {
                "view@default": {
                    templateUrl: 'rms/orders/pending-orders/pending-orders.html',
                    controller: 'PendingOrdersController',
                    controllerAs: 'vm',
                    params: ['id']
                }
            }
        }).state('waiting_orders', {
            parent: 'default',
            url: '/waiting_orders',
            views: {
                "view@default": {
                    templateUrl: 'rms/orders/waiting-orders/waiting-orders.html',
                    controller: 'WaitingOrdersController',
                    controllerAs: 'vm'
                }
            }
        }).state('admin_waiting_orders', {
            parent: 'default',
            url: '/admin_waiting_orders',
            views: {
                "view@default": {
                    templateUrl: 'rms/admin/orders/waiting-orders/admin-waiting-orders.html',
                    controller: 'AdminWaitingOrdersController',
                    controllerAs: 'vm'
                }
            }
        }).state('admin_order_detail', {
            parent: 'default',
            url: '/admin_order_detail/:orderId',
            views: {
                "view@default": {
                    templateUrl: 'rms/admin/orders/order-detail/admin-order-detail.html',
                    controller: 'AdminOrderDetailController',
                    controllerAs: 'vm'
                }
            }
        }).state('served_orders', {
            parent: 'default',
            url: '/served_orders',
            views: {
                "view@default": {
                    templateUrl: 'rms/orders/served-orders/served-orders.html',
                    controller: 'ServedOrdersController',
                    controllerAs: 'vm'
                }
            }
        }).state('order_detail', {
            parent: 'default',
            url: '/order_detail/:orderId',
            views: {
                "view@default": {
                    templateUrl: 'rms/orders/order-detail/order-detail.html',
                    controller: 'OrderDetailController',
                    controllerAs: 'vm'
                }
            }
        }).state('token_management', {
            parent: 'default',
            url: '/token_management',
            views: {
                "view@default": {
                    templateUrl: 'rms/tokens/token-management/token-management.html',
                    controller: 'TokenManagementController',
                    controllerAs: 'vm',
                    params: ['token']
                }
            }
        }).state('tokens_history', {
            parent: 'default',
            url: '/token_history',
            views: {
                "view@default": {
                    templateUrl: 'rms/tokens/tokens-history/tokens-history.html',
                    controller: 'TokensHistoryController',
                    controllerAs: 'vm'
                }
            }
        }).state('cash_drawer_report', {
            parent: 'default',
            url: '/cash_drawer_report',
            views: {
                "view@default": {
                    templateUrl: 'rms/reports/cash-drawer-report/cash-drawer-report.html',
                    controller: 'CashDrawerReportController',
                    controllerAs: 'vm'
                }
            }
        }).state('sales_report', {
            parent: 'default',
            url: '/sales_report',
            views: {
                "view@default": {
                    templateUrl: 'rms/reports/sales-report/sales-report.html',
                    controller: 'SalesReportController',
                    controllerAs: 'vm'
                }
            }
        }).state('end_of_the_day_report', {
            parent: 'default',
            url: '/end_of_the_day_report',
            views: {
                "view@default": {
                    templateUrl: 'rms/reports/end-of-the-day-report/end-of-the-day-report.html',
                    controller: 'EndOfTheDayReportController',
                    controllerAs: 'vm'
                }
            }
        }).state('token_details', {
            parent: 'default',
            url: '/token_details/:tokenId',
            views: {
                "view@default": {
                    templateUrl: 'rms/tokens/token-details/token-details.html',
                    controller: 'TokenDetailsController',
                    controllerAs: 'vm'
                }
            }
        }).state('404', {
            parent: 'default',
            url: '/404',
            views: {
                "view@default": {
                    templateUrl: 'rms/core/not-found.html',
                    controller: 'NotFoundController',
                    controllerAs: 'vm'
                }
            }
        });

        $urlRouterProvider.otherwise('/');
    }]);
})();
