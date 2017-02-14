// config

var app = angular.module('app');
app.factory('httpInterceptor', [ '$q', '$injector',function($q, $injector) {
    return {
        'responseError': function (response) {
            if (response.status == 401) {
                var rootScope = $injector.get('$rootScope');
                sessionStorage.removeItem('token');
                rootScope.$state.go('signin');
                $.notify({
                    message: "<div style='text-align: center'><i class='fa fa-warning'></i> 身份验证异常，请重新登录！</div>"
                },{
                    type:"danger",
                    placement:{
                        align:"center"
                    }
                });
            }
            return $q.reject(response);
        },
        'response': function (response) {
            var token = response.headers('x-cms-token');
            if (token) {
                sessionStorage.token = token;
            }
            return response;
        },
        'request': function (config) {
            var token;
            if (token = sessionStorage.getItem('token')) {
                config.headers['x-cms-token'] = token;
            }
            return config;
        },
        'requestError': function (config) {
            return $q.reject(config);
        }
    };
}]);

app.config(
    [        '$controllerProvider', '$compileProvider', '$filterProvider', '$provide', '$locationProvider','$httpProvider',
    function ($controllerProvider,   $compileProvider,   $filterProvider,   $provide ,$locationProvider, $httpProvider) {
        // lazy controller, directive and service
        app.controller = $controllerProvider.register;
        app.directive  = $compileProvider.directive;
        app.filter     = $filterProvider.register;
        app.factory    = $provide.factory;
        app.service    = $provide.service;
        app.constant   = $provide.constant;
        app.value      = $provide.value;
        $httpProvider.interceptors.push('httpInterceptor');
    }
  ]);

app.constant('host','');