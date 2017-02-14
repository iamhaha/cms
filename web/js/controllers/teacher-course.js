/**
 * Created by fangf on 2016/5/22.
 */
app.controller("TeacherCourseCtrl",function ($scope,$http,host,SweetAlert,$state) {
    $http.post(host+"v1/teacher/course/list").success(function (d) {
        if (d.code==0)
            $scope.courses = d.data;
        if (d.data.length==0) $('.table').dataTable();
    });
});