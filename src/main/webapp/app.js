(function () {
    'use strict';

    angular.module('myApp', [
        'myApp.services',
        'ui.router'
    ]).config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $stateProvider.state('default', {
            url: '',
            views: {
                "body@": {
                    templateUrl: 'rms/core/body.html',
                    controller: 'BodyController',
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
        }).state('order_payment', {
            parent: 'default',
            url: '/order_payment/:orderId',
            views: {
                "view@default": {
                    templateUrl: 'rms/orders/order-payment/order-payment.html',
                    controller: 'OrderPaymentController',
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
        }).state('income_reports', {
            parent: 'default',
            url: '/income_reports',
            views: {
                "view@default": {
                    templateUrl: 'rms/reports/income-reports/income-reports.html',
                    controller: 'IncomeReportsController',
                    controllerAs: 'vm'
                }
            }
        }).state('orders_summary', {
            parent: 'default',
            url: '/orders_summary',
            views: {
                "view@default": {
                    templateUrl: 'rms/reports/orders-summary/orders-summary.html',
                    controller: 'OrdersSummaryController',
                    controllerAs: 'vm'
                }
            }
        }).state('tokens_summary', {
            parent: 'default',
            url: '/tokens_summary',
            views: {
                "view@default": {
                    templateUrl: 'rms/reports/tokens-summary/tokens-summary.html',
                    controller: 'TokensSummaryController',
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

        $urlRouterProvider.otherwise('/404');
    }]);
})();
