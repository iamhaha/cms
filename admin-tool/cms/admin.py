# -*- coding: utf-8 -*-

import json
from cement.core import foundation, controller, handler
import cms_client

class MyAppBaseController(controller.CementBaseController):
    """ base controller """
    class Meta(object):
        """ meta info """
        label = 'base'
        description = 'cms admin tool'


class TeacherController(controller.CementBaseController):
    """ teacher """
    class Meta(object):
        """ meta info """
        description = u'teacher related operations'
        label = 'teacher'
        stacked_on = 'base'
        stacked_type = 'nested'


class TeacherListController(controller.CementBaseController):
    """
    list teacher controller
    """
    class Meta(object):
        """ meta info """
        description = 'list teacher'
        label = 'teacher_list'
        stacked_on = 'teacher'
        stacked_type = 'nested'
        aliases = ['list']
        aliases_only = True
        interface = controller.IController

    @controller.expose(hide=True)
    def default(self):
        """ nothing """
        success, response = cms_client.teacher_list()
        if success:
            print get_pretty_print(response)
        else:
            print response


class ClassController(controller.CementBaseController):
    """ class """
    class Meta(object):
        """ meta info """
        description = u'class related operations'
        label = 'class'
        stacked_on = 'base'
        stacked_type = 'nested'


class ClassListController(controller.CementBaseController):
    """
    list class controller
    """
    class Meta(object):
        """ meta info """
        description = 'list class'
        label = 'class_list'
        stacked_on = 'class'
        stacked_type = 'nested'
        aliases = ['list']
        aliases_only = True
        interface = controller.IController

    @controller.expose(hide=True)
    def default(self):
        """ nothing """
        success, response = cms_client.class_list()
        if success:
            print get_pretty_print(response)
        else:
            print response


class StudentController(controller.CementBaseController):
    """ student """
    class Meta(object):
        """ meta info """
        description = u'student related operations'
        label = 'student'
        stacked_on = 'base'
        stacked_type = 'nested'


class StudentListController(controller.CementBaseController):
    """
    list student controller
    """
    class Meta(object):
        """ meta info """
        description = 'list student'
        label = 'student_list'
        stacked_on = 'student'
        stacked_type = 'nested'
        aliases = ['list']
        aliases_only = True
        interface = controller.IController

    @controller.expose(hide=True)
    def default(self):
        """ nothing """
        success, response = cms_client.student_list()
        if success:
            print get_pretty_print(response)
        else:
            print response


class CourseController(controller.CementBaseController):
    """ course """
    class Meta(object):
        """ meta info """
        description = u'course related operations'
        label = 'course'
        stacked_on = 'base'
        stacked_type = 'nested'


class CourseListController(controller.CementBaseController):
    """
    list course controller
    """
    class Meta(object):
        """ meta info """
        description = 'list course'
        label = 'course_list'
        stacked_on = 'course'
        stacked_type = 'nested'
        aliases = ['list']
        aliases_only = True
        interface = controller.IController

    @controller.expose(hide=True)
    def default(self):
        """ nothing """
        success, response = cms_client.course_list()
        if success:
            print get_pretty_print(response)
        else:
            print response


class UserController(controller.CementBaseController):
    """ user """
    class Meta(object):
        """ meta info """
        description = u'user related operations'
        label = 'user'
        stacked_on = 'base'
        stacked_type = 'nested'


class UserChangePasswordController(controller.CementBaseController):
    """
    list course controller
    """
    class Meta(object):
        """ meta info """
        description = 'user change password'
        label = 'user_changepwd'
        stacked_on = 'user'
        stacked_type = 'nested'
        aliases = ['changepwd']
        aliases_only = True
        interface = controller.IController

        arguments = [
            (['-ID', '--id'], dict(help=u'user id')),
            (['-PWD', '--password'], dict(help=u'user new password'))
        ]

    @controller.expose(hide=True)
    def default(self):
        """ nothing """
        success, response = cms_client.user_changepassword(self.app.pargs.id,
                                                           self.app.pargs.password)
        if success:
            print "success"
        else:
            print response


def get_pretty_print(json_object):
    """ print json """
    return json.dumps(json_object, sort_keys=True, indent=4, separators=(',', ': '))


def main():
    """ main """
    try:
        # create the application
        app = foundation.CementApp('myapp')

        handler.register(MyAppBaseController)

        handler.register(TeacherController)
        handler.register(TeacherListController)

        handler.register(ClassController)
        handler.register(ClassListController)

        handler.register(StudentController)
        handler.register(StudentListController)

        handler.register(CourseController)
        handler.register(CourseListController)

        handler.register(UserController)
        handler.register(UserChangePasswordController)

        # setup the application
        app.setup()

        app.run()
    finally:
        app.close()


if __name__ == '__main__':
    main()