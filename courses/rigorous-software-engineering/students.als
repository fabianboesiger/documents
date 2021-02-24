open util/boolean

abstract sig Student {
	-- If we want to model non-legal studens, students 
	-- without University should also be allowed.
	university: lone University,
	isLegal: Bool,
	id: one ID,
	mayor: one Mayor,
	classmates: set Student
}
sig Undergraduate extends Student {}
sig Graduate extends Student {}
sig ID {}
sig University {}
sig Mayor {}

-- Every id should be unique.
fact uniqueIds {
	all disj s1, s2: Student | s1.id != s2.id
}

-- The number of universities may be one if and only if
-- the student is legal.
fact legalStudents {
	all s: Student | 
		#(s.university) = 1 <=> s.isLegal = True
}

fact classmatesRestriction {
	-- Graduates and undergraduates are never classmates.
	all s1: Graduate | all s2: Undergraduate |
		s2 not in s1.classmates and s1 not in s2.classmates
	-- A student cannot be his own classmate.
	all s: Student | s not in s.classmates
	-- All students at the same university with the same mayor are classmates.
	all disj s1, s2: Student |
		s1.university = s2.university and s1.mayor = s2.mayor <=> s1 in s2.classmates
}

pred show {}
run show for 5
