/**
 * Created by fangf on 2016/5/22.
 */
app.controller("StudentAllCourseCtrl",function ($scope,$http,host,SweetAlert,$state) {
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
            $scope.courseMap = {};
            d.data.map(function (i) {
                $scope.courseMap[i.id] = true;
            })
        }
    });
    $http.post(host+"v1/student/course/listall").success(function (d) {
        if (d.code==0) {
            $scope.allcourses = d.data;
        }
        if (d.data.length==0) $('.table').dataTable();
    });
    $scope.take = function () {
        var ids = getSelectedList().map(function (i) {
            return $scope.allcourses[i].id;
        });
        if (ids.length==0){
            SweetAlert.swal("提示", "请选择课程", "warning");
            return;
        }
        SweetAlert.swal({
                title: "提示",
                text: "确定选课？",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: false},
            function(ok){
                if (ok)
                    $http.post(host+"/v1/student/course/take",{ids:ids}).success(function (d) {
                        if (d.code==0) {
                            SweetAlert.swal("选课成功", "请到“我的课程”中查看您的课程", "success");
                            $state.reload();
                        }
                        else SweetAlert.swal("选课失败", d.message, "error");
                    });
            });
    }
});