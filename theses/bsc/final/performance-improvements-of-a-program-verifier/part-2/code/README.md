# Part Two Notes

## Bug Description

The current implementation of joining has introduced a bug which in a few cases (7 of the 333 tests used in the benchmark were affected) leads to a failure in the interaction with the SMT solver Z3 that Silicon uses internally. Simply put, the merging of some quantified heap chunks may result in a Z3 warning because two triggers, that for themselves work flawlessly, may result in a new, invalid trigger when merged. The exact details or an approach to fix this bug are currently unclear and should be further examined.

The benchmark `part-2/benchmarks/more-joins/partition-by-verification-time.ods` contains a column named "Z3 WARNING" which indicates whether the bug occurred in the corresponding test.

## Failed Tests Overview

This table contains an overview of the tests that fail using command-line arguments `--moreJoins` and `--moreCompleteExhale` enabled.

|Test Name|Cause|Notes|
|---|---|---|
|`all/issues/silicon/0030.vpr`|Insufficient permission||
|`all/issues/silicon/0072.vpr`|Unexpected output did not occur|Fixed due to more complete exhale (#72)|
|`all/issues/silicon/0114.vpr`|Unexpected output did not occur|Fixed due to more complete exhale (#114, Missing Output)|
|`all/issues/silicon/0232.vpr`|Unexpected output did not occur|Fixed due to more complete exhale (#232)|
|`all/issues/silicon/0240.vpr`|Insufficient permission||
|`all/issues/silicon/0334.vpr`|Assert might fail||
|`all/predicates/arguments.vpr`|Unexpected output did not occur|Fixed due to more complete exhale (#36, Missing Output)|
|`quantifiedpermissions/issues/issue_0184.vpr`|Unexpected output did not occur|Fixed due to more complete exhale (#184)|
|`quantifiedpermissions/sequences/bsearch.vpr`|Timeout occured||
|`quantifiedpermissions/sequences/self_framing.vpr`|Unexpected output did not occur|Fixed due to more complete exhale (#72)|
|`quantifiedpermissions/sets/generalized_shape.vpr`|Verifiaction aborted exceptionally||
|`quantifiedpredicates/issues/unfolding.vpr`|Unexpected output did not occur|Fixed due to more complete exhale (#158)|
|`wands/regression/PackageStateConsolidation.vpr`|Insufficient permission||
|`wands/regression/conditionals3.vpr`|Assert might fail||
|`examples/linked-list-predicates.vpr`|Invariant not preserved||
|Various symbExLogTests||This is expected as the structure of the symbolic execution log changes when execution paths are joined that weren't joined previously.|