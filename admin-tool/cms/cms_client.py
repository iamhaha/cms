# -*- coding: utf-8 -*-

import json
import ConfigParser
import requests

def config_init():
    """
    :return:
    """
    global SERVER
    config = ConfigParser.RawConfigParser()
    config.read('config.ini')
    SERVER = 'http://{}:{}'.format(config.get('server', 'host'), config.get('server', 'port'))


def get_request(uri='', method = 'POST'):
    """
    :param uri:
    :param method:
    :return:
    """
    return {
        'method': method,
        'uri': uri,
        'params': {
        },
        'headers': {
            'content-type': 'application/json',
            'x-cms-token': 'abcdefgABCDEFG0123456789'
        }
    }


def simple_post(request, payload = None):
    if payload is None:
        return requests.post('{}{}'.format(SERVER, request['uri']),
                             headers=request['headers'])
    else:
        return requests.post('{}{}'.format(SERVER, request['uri']),
                             data=json.dumps(payload),
                             headers=request['headers'])

def response_parser(func):
    """
    :param func:
    :return:
    """
    def inner(*args, **kwargs):
        """ inner """
        response = func(*args, **kwargs)
        if response.status_code // 100 == 2:
            json = response.json()
            if json.has_key('code') and json['code'] == 0:
                return True, json['data'] if json.has_key('data') else None
            else:
                return False, json['message']
        else:
            return False, response.text

    return inner


config_init()

# teacher apis
@response_parser
def teacher_list():
    """
    :return:
    """
    return simple_post(get_request('/v1/admin/teacher/list'))


# class apis
@response_parser
def class_list():
    """
    :return:
    """
    return simple_post(get_request('/v1/admin/class/list'))


# student apis
@response_parser
def student_list():
    """
    :return:
    """
    return simple_post(get_request('/v1/admin/student/list'))


# course apis
@response_parser
def course_list():
    """
    :return:
    """
    return simple_post(get_request('/v1/admin/course/list'))


# misc apis
@response_parser
def user_changepassword(id, password):
    """
    :param id:
    :param password:
    :return:
    """
    payload = {
        'id': id,
        'password': password
    }
    return simple_post(get_request('/v1/admin/user/changepwd'), payload)