/**
 * Created by fangf on 2016/5/21.
 */
app.controller('ChangePwdCtrl',function ($scope,$http,host,$timeout,SweetAlert,$state) {
    $scope.submit = function () {
        $scope.check = true;
        $timeout(function () {
            if ($("form .has-error").length>0)
                return;
            $http.post(host+"v1/user/changepwd",{password:$scope.password,old:$scope.oldPwd})
                .success(function (d) {
                    if (d.code==0){
                        $scope.logout();
                        SweetAlert.swal('修改成功','修改密码成功，请重新登录。','success');
                        $state.go('signin')
                    } else SweetAlert.swal('修改失败',d.message,'error');
                });
        },100)
    }
});