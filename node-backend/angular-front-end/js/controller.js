/*
 * MIT License
 *
 * Copyright (c) 2018 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
(function () {
    var person = {};
    var app = angular.module('controller', []);
    app.controller('personCtrl', function ($scope, $state, personSrv) {
        function getAll() {
            $scope.datas = personSrv.query();
        };
        getAll();
        $scope.add = function () {
            person = {};
            $state.go('form');
        };
        $scope.edit = function (data) {
            person = data;
            $state.go('form');
        };
        $scope.remove = function (id){
            var r = confirm("Você tem certeza?");
            if (r === true) {
                personSrv.delete({id: id}, function () {
                    getAll();
                });
            } else {
                getAll();
            }
        };
    });
    app.controller('formCtrl', function ($scope, $state, personSrv) {
        $scope.post = person;
        $scope.back = function () {
            $state.go('person');
        };
        $scope.save = function () {
            if ($scope.post.id === undefined) {
                personSrv.save($scope.post, function () {
                    $scope.post = '';
                    $state.go('person');
                });
            } else {
                personSrv.update($scope.post, function () {
                    $state.go('person');
                });
            }
        };
    });
})();
