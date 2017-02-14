/**
 * Created by fangf on 2016/5/23.
 */
app.controller("CourseDetailCtrl",function ($scope,$stateParams,$http,host, SweetAlert) {
    var cid = $stateParams.cid;
    $http.post(host+"v1/teacher/course/get?id="+cid).success(function (d) {
        if (d.code==0){
            $scope.course = d.data.course;
            $scope.students = d.data.students;
        }
        if ($scope.students.length==0) $(".table").dataTable();
    });
});