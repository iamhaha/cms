/**
 * Created by fangf on 2016/5/21.
 */
app.controller('StudentManageCtrl',function ($scope,$modal,$http,host,$state,SweetAlert,$timeout) {
    var classes = {};
    $http.post(host+'v1/admin/class/list').success(function (d) {
        if (d.code==0) {
            classes = d.data;
            // $scope.classMap = {};
            // d.data.map(function (i) {
            //     $scope.classMap[i.id] = i.name;
            // });
        }
    });
    $http.post(host+'v1/admin/student/list').success(function (d) {
        if (d.code==0)
            $scope.students = d.data;
        if (d.data.length==0) $('.table').dataTable();
    });
    $scope.add = function () {
        $modal.open({
            templateUrl: 'add.html',
            controller: function($scope,$modalInstance){
                $scope.classes = classes;
                $scope.student = {};
                $scope.ok = function(){
                    $scope.check = true;
                    $timeout(function () {
                        if ($('form .has-error').length>0)
                            return;
                        $http.post(host+'v1/admin/student/create',$scope.student).success(function (d) {
                            if (d.code==0)
                                SweetAlert.swal("增加成功", "新增“"+$scope.student.name+"”", "success");
                            else
                                SweetAlert.swal("增加失败", d.message, "error");
                            $modalInstance.close();
                            $state.reload();
                        });
                    },100);
                };
                $scope.cancel = function(){
                    $modalInstance.dismiss();
                }
            },
            size: 'md'
        });
    };
    $scope.del = function () {
        var ids = getSelectedList().map(function (i) {
            return $scope.students[i].id;
        });
        if (ids.length==0){
            SweetAlert.swal("提示", "请选择删除的学生", "warning");
            return;
        }
        SweetAlert.swal({
                title: "提示",
                text: "确定删除选中学生？",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: false},
            function(ok){
                if (ok) {
                    $http.post(host+'v1/admin/student/delete',{ids:ids}).success(function (d) {
                        if (d.code==0){
                            SweetAlert.swal("删除成功", "成功删除选中的学生", "success");
                            $state.reload();
                        }
                        else
                            SweetAlert.swal("删除失败", d.message, "error");
                    });
                }
            });
    };
});