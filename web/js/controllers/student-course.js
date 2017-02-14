/**
 * Created by fangf on 2016/5/23.
 */
app.controller("StudentCourseCtrl",function ($scope,$http,host,SweetAlert,$state) {
    $http.post(host+'v1/student/teacher/list').success(function (d) {
        if (d.code==0) {
            $scope.teacherMap = {};
            d.data.map(function (i) {
                $scope.teacherMap[i.id] = i.name;
            });
        }
    });
    $http.post(host+"v1/student/course/list").success(function (d) {
        if (d.code==0) {
            $scope.courses = d.data;
        }
        if (d.data.length==0) $('.table').dataTable();
    });
    $scope.drop = function () {
        var ids = getSelectedList().map(function (i) {
            return $scope.courses[i].id;
        });
        if (ids.length==0){
            SweetAlert.swal("提示", "请选择退选的课程", "warning");
            return;
        }
        SweetAlert.swal({
                title: "提示",
                text: "确定退课？",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: false},
            function(ok){
                if (ok)
                    $http.post(host+"v1/student/course/drop",{ids:ids}).success(function (d) {
                        if (d.code==0) {
                            SweetAlert.swal("退课成功", "", "success");
                            $state.reload();
                        }
                        else SweetAlert.swal("退课失败", "", "error");
                    });
            });
    }
});