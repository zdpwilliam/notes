#!/usr/bin/python
# -*- coding: UTF-8 -*-

# 书中一个例子简单的短期利率类，折现是金融学中最基本的概念之一，在连续折现的固定短期利率世界中，
# 日期t>0时未来现金流与当前日期t=0之间的折现因子为Do(t)=e（-rt）次方
import numpy as np


class short_rate(object):
    def __init__(self, name, rate):
        self.name = name
        self.rate = rate

    def get_discount_factors(self, time_list):
        time_list = np.array(time_list)
        return np.exp(-self.rate * time_list)


class cash_flow_series(object):
    def __init__(self, name, time_list, cash_flows, short_rate):
        self.name = name
        self.time_list = time_list
        self.cash_flows = cash_flows
        self.short_rate = short_rate

    def present_value_list(self):
        df = self.short_rate.get_discount_factors(self.time_list)
        # 想问这一行中为什么可以这样子不继承第一个类可以调用第一个类的函数呢?
        # https://www.zhihu.com/question/20021164回答了
        return np.array(self.cash_flows) * df

    def net_present_value(self):
        return np.sum(self.present_value_list())


class cfs_sensitivity(cash_flow_series):
    def npv_sensitivity(self, short_rates):
        npvs = []
        for rate in short_rates:
            sr.rate = rate  # 这个实例化rate的意思是？
            npvs.append(self.net_present_value())
        return np.array(npvs)


short_rates = [0.01, 0.025, 0.05, 0.075, 0.1, 0.125, 0.15, 0.2]
cash_flows = np.array([-100, 50, 75])
time_list = [0.0, 1.0, 2.0]

sr = short_rate('r', 0.05)
print sr.get_discount_factors(time_list)

sr.rate = 0.05
cfs = cash_flow_series('cfs0', time_list, cash_flows, sr)
print cfs.present_value_list()

cfs_sens = cfs_sensitivity('cfs', time_list, cash_flows, sr)

npvs = cfs_sens.npv_sensitivity(short_rates)
print npvs