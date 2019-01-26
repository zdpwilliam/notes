__author__ = 'william.zhang'

class Person:
    def __init__(self, name):
        self.name = name

    def can_be_lovers(self, pa, pb):
        if pa.gender != pb.gender:
            print "%s and %s can be a lovers!" % (pa.gender, pb.gender)
        else:
            print "%s and %s cannot be a lovers!" % (pa.gender, pb.gender)

class Man(Person):
    def __init__(self):
        self.gender = 'man'
    def __str__(self):
        return "gender is %s" % (self.gender)
    def setGender(self, gender):
        self.gender = gender

class Woman(Person):
    def __init__(self):
        self.gender = 'woman'
    def __str__(self):
        return "gender is %s" % (self.gender)

p = Person("Judge")
print p

pa = Man()
print pa

pb = Woman()
print pb

p.can_be_lovers(pa, pb)

pa.setGender("woman")
print pb

p.can_be_lovers(pa, pb)