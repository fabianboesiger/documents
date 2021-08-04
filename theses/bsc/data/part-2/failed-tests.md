# Failed Tests Overview

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