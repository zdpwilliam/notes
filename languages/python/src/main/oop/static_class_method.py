#!/usr/bin/python
# -*- coding: UTF-8 -*-


class Kls(object):
    def __init__(self, data):
        self.data = data

    def printd(self):
        print(self.data)

    @staticmethod
    def smethod(*arg):
        print('Static:', arg)

    @classmethod
    def cmethod(*arg):
        print('Class:', arg)


ik = Kls(23)
ik.printd()
ik.smethod()
ik.cmethod()

Kls.printd()    #会报异常TypeError: unbound method printd() must be called with Kls
                # instance as first argument (got nothing instead)
Kls.smethod()
Kls.cmethod()
