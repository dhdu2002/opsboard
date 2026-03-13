# OpsBoard JUnit Test Guide

## 1) Goal

This document standardizes how to write, run, and archive tests for OpsBoard.

- Current scope: unit tests for in-memory stores, auth, and util logic.
- Future scope: every new feature must include test cases and execution logs.

## 2) Test Location and Naming Rules

- Test root: `src/test/java`
- Naming rule: `<TargetClassName>Test`
- Method naming: `targetMethod_whenCondition_expectedResult`

Examples:

- `ServerStoreTest`
- `ActivityStoreTest`
- `WebParamUtilsTest`

## 3) Current Test Inventory

### Basic

- `src/test/java/com/opsboard/SanityTest.java`

### Util/Auth

- `src/test/java/com/opsboard/common/util/WebParamUtilsTest.java`
- `src/test/java/com/opsboard/auth/store/UserStoreTest.java`
- `src/test/java/com/opsboard/auth/service/impl/AuthServiceImplTest.java`

### Core Stores

- `src/test/java/com/opsboard/server/store/ServerStoreTest.java`
- `src/test/java/com/opsboard/site/store/SiteStoreTest.java`
- `src/test/java/com/opsboard/account/store/AccountStoreTest.java`
- `src/test/java/com/opsboard/issue/store/IssueStoreTest.java`
- `src/test/java/com/opsboard/activity/store/ActivityStoreTest.java`

### Category Stores

- `src/test/java/com/opsboard/server/store/ServerCategoryStoreTest.java`
- `src/test/java/com/opsboard/site/store/SiteCategoryStoreTest.java`
- `src/test/java/com/opsboard/account/store/AccountCategoryStoreTest.java`
- `src/test/java/com/opsboard/issue/store/IssueCategoryStoreTest.java`

## 4) How Tests Run (Execution Flow)

When running `mvn test`:

1. Maven compiles main/test sources.
2. Surefire finds test classes in `src/test/java`.
3. JUnit executes methods annotated with `@Test`.
4. Result summary is printed in console.
5. Detailed files are generated under `target/surefire-reports`.

## 5) How to Run Tests

## Maven (recommended)

Run all tests:

```bash
mvn test
```

Run one test class:

```bash
mvn -Dtest=ServerStoreTest test
```

Run specific methods:

```bash
mvn -Dtest=ActivityStoreTest#findByCondition_filtersByKeywordModuleAndAction test
```

## Eclipse

1. Right-click test class or method.
2. `Run As > JUnit Test`.
3. Check JUnit view:
   - green bar: pass
   - red bar: failure
4. Check Console + stack trace for details.

## 6) Where Results Are Stored

- Console: quick summary (`Tests run`, `Failures`, `Errors`, `Skipped`)
- Files:
  - `target/surefire-reports/TEST-*.xml`
  - `target/surefire-reports/*.txt`

If console output looks minimal, open JUnit view or Surefire report files.

## 7) Current Verification Log

| Date | Command | Result | Notes |
|---|---|---|---|
| 2026-03-13 | `mvn test` | Not executed in CLI env | `mvn` command unavailable in this environment |

Record local/Eclipse execution below after each run.

## 8) Future Feature Test Policy (Mandatory)

For every new feature:

1. Add at least 1 positive case and 1 negative/boundary case.
2. Add/update a test class in `src/test/java`.
3. Run full tests (`mvn test`) before commit.
4. Append one row in "Execution Log" below.

## 9) Execution Log Template (Keep Updating)

| Date | Feature | Added/Updated Test Files | Command | Pass/Fail | Failure Reason | Action Taken |
|---|---|---|---|---|---|---|
| YYYY-MM-DD | example: server category filter | `src/test/java/...` | `mvn test` | PASS/FAIL | - | - |

## 10) Beginner Writing Checklist

- Start with store/util logic first (no servlet mocking needed).
- Use unique tokens (`UUID`) to isolate test data.
- Follow Given-When-Then structure.
- Always clean up data if test inserts records.
- Keep assertions explicit and focused.
