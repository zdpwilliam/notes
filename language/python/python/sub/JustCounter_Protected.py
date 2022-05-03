#!/usr/bin/python
# -*- coding: UTF-8 -*-
from python.JustCounter import *


counter = JustCounter()
print counter.publicCount
print counter._protectedCount
print JustCounter.publicCount