/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
(function (ng) {

    var mod = ng.module("raitingModule");

    mod.controller("raitingListCtrl", ["$scope", '$state', 'raitings', '$stateParams','Restangular',
        function ($scope, $state, raitings, $params,Restangular) {
            $scope.records = raitings;

            //Paginación
            this.itemsPerPage = $params.limit;
            this.currentPage = $params.page;
            this.totalItems = raitings.totalRecords;
            
            $scope.categorys = [];
            
      
            $scope.filtrar = function (parentCategory) {
                Restangular.all("raitings").customGET(parentCategory).then(function (response) {                    
                        $scope.records=response;
                });
            };

            this.pageChanged = function () {
                $state.go('raitingList', {page: this.currentPage});
            };
            
            $scope.actions = {
                create: {
                    displayName: 'Create Raiting',
                    icon: 'plus',
                    fn: function () {
                        $state.go('raitingNew');
                    }
                },
                refresh: {
                    displayName: 'Refresh Raitings',
                    icon: 'refresh',
                    fn: function () {
                        $state.reload();
                    }
                },
                cancel: {
                    displayName: 'Go back',
                    icon: 'arrow-left',
                    fn: function () {
                        $state.go('tripDetail');
                    }
                }

            };
            
            // actions only in home view
            if ($scope.isHomeView)
            {
                $scope.actions = {
                refresh: {
                    displayName: 'Refresh Raitings',
                    icon: 'refresh',
                    fn: function () {
                        $state.reload();
                    }
                }
               };
            }
            
            $scope.recordActions = {
                detail: {
                    displayName: 'Detail',
                    icon: 'eye-open',
                    fn: function (rc) {
                        $state.go('raitingDetail', {raitingId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                },
                edit: {
                    displayName: 'Edit',
                    icon: 'edit',
                    fn: function (rc) {
                        $state.go('raitingEdit', {raitingId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                },
                delete: {
                    displayName: 'Delete',
                    icon: 'minus',
                    fn: function (rc) {
                        $state.go('raitingDelete', {raitingId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                }
            };
            
            // actions only in home view
            if ($scope.isHomeView)
            {
                $scope.recordActions = {};
            }
        }]);
})(window.angular);
