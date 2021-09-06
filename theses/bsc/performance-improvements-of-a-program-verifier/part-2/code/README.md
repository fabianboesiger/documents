The current implementation of joining has introduced a bug which in a few cases (7 of the 333 tests used in the benchmark were affected) leads to a failure in the interaction with the SMT solver Z3 that Silicon uses internally. Simply put, the merging of some quantified heap chunks may result in a Z3 warning because two triggers, that for themselves work flawlessly, may result in a new, invalid trigger when merged. The exact details or an approach to fix this bug are currently unclear and should be further examined.

The benchmark `part-2/benchmarks/more-joins/partition-by-verification-time.ods` contains a column named "Z3 WARNING" which indicates whether the bug occurred in the corresponding test.

