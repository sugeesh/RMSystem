(function () {
    'use strict';

    angular.module('myApp', [
        'myApp.services',
        'ui.router',
        'LocalStorageModule',
        'ngSessionStorage'
    ]).config(['$stateProvider', '$urlRouterProvider', 'localStorageServiceProvider', function ($stateProvider, $urlRouterProvider, localStorageServiceProvider) {

        $stateProvider.state('default', {
            url: '/',
            views: {
                "content": {
                    templateUrl: 'rms/core/main.html',
                    controller: 'MainController',
                    controllerAs: 'vm'
                }
            }
        }).state('login', {
            url: '/login',
            views: {
                "content": {
                    templateUrl: 'rms/core/login.html',
                    controller: 'LoginController',
                    controllerAs: 'vm'
                }
            }
        }).state('register', {
            url: '/register',
            views: {
                "content": {
                    templateUrl: 'rms/core/register.html',
                    controller: 'RegisterController',
                    controllerAs: 'vm'
                }
            }
        }).state('dashboard', {
            url: '/dashboard',
            views: {
                "content": {
                    templateUrl: 'rms/dashboard/dashboard.html',
                    controller: 'DashboardController',
                    controllerAs: 'vm'
                }
            },
        }).state('settings', {
            url: '/settings',
            views: {
                "content": {
                    templateUrl: 'rms/core/settings.html',
                    controller: 'SettingsController',
                    controllerAs: 'vm'
                }
            }
        }).state('menu', {
            url: '/menu',
            views: {
                "content": {
                    templateUrl: 'rms/menu/menu-management/menu-management.html',
                    controller: 'MenuManagementController',
                    controllerAs: 'vm'
                }
            }
        }).state('order_management', {
            url: '/order_management',
            views: {
                "content": {
                    templateUrl: 'rms/orders/order-management/order-management.html',
                    controller: 'OrderManagementController',
                    controllerAs: 'vm'
                }
            }
        }).state('order_update', {
            url: '/order_update/:orderId',
            views: {
                "content": {
                    templateUrl: 'rms/orders/order-update/order-update.html',
                    controller: 'OrderUpdateController',
                    controllerAs: 'vm'
                }
            }
        }).state('orders_history', {
            url: '/orders_history',
            views: {
                "content": {
                    templateUrl: 'rms/orders/orders-history/orders-history.html',
                    controller: 'OrdersHistoryController',
                    controllerAs: 'vm'
                }
            }
        }).state('pending_orders', {
            url: '/pending_orders',
            views: {
                "content": {
                    templateUrl: 'rms/orders/pending-orders/pending-orders.html',
                    controller: 'PendingOrdersController',
                    controllerAs: 'vm',
                    params: ['id']
                }
            }
        }).state('served_orders', {
            url: '/served_orders',
            views: {
                "content": {
                    templateUrl: 'rms/orders/served-orders/served-orders.html',
                    controller: 'ServedOrdersController',
                    controllerAs: 'vm'
                }
            }
        }).state('order_payment', {
            url: '/order_payment/:orderId',
            views: {
                "content": {
                    templateUrl: 'rms/orders/order-payment/order-payment.html',
                    controller: 'OrderPaymentController',
                    controllerAs: 'vm'
                }
            }
        }).state('token_management', {
            url: '/token_management',
            views: {
                "content": {
                    templateUrl: 'rms/tokens/token-management/token-management.html',
                    controller: 'TokenManagementController',
                    controllerAs: 'vm',
                    params: ['token']
                }
            }
        }).state('tokens_history', {
            url: '/token_history',
            views: {
                "content": {
                    templateUrl: 'rms/tokens/tokens-history/tokens-history.html',
                    controller: 'TokensHistoryController',
                    controllerAs: 'vm'
                }
            }
        }).state('income_reports', {
            url: '/income_reports',
            views: {
                "content": {
                    templateUrl: 'rms/reports/income-reports/income-reports.html',
                    controller: 'IncomeReportsController',
                    controllerAs: 'vm'
                }
            }
        }).state('orders_summary', {
            url: '/orders_summary',
            views: {
                "content": {
                    templateUrl: 'rms/reports/orders-summary/orders-summary.html',
                    controller: 'OrdersSummaryController',
                    controllerAs: 'vm'
                }
            }
        }).state('tokens_summary', {
            url: '/tokens_summary',
            views: {
                "content": {
                    templateUrl: 'rms/reports/tokens-summary/tokens-summary.html',
                    controller: 'TokensSummaryController',
                    controllerAs: 'vm'
                }
            }
        }).state('token_details', {
            url: '/token_details/:tokenId',
            views: {
                "content": {
                    templateUrl: 'rms/tokens/token-details/token-details.html',
                    controller: 'TokenDetailsController',
                    controllerAs: 'vm'
                }
            }
        }).state('404', {
            url: '/404',
            views: {
                "content": {
                    templateUrl: 'rms/core/not-found.html',
                    controller: 'NotFoundController',
                    controllerAs: 'vm'
                }
            }
        });

        $urlRouterProvider.otherwise('/404');

        localStorageServiceProvider
            .setPrefix('myApp')
            .setStorageType('sessionStorage')
            .setNotify(true, true);

    }]);
})();
