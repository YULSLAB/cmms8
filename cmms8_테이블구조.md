---
## CMMS DB 테이블 정의서

### 1. 공통 데이터 관리 (`common` 모듈)

모든 모듈에서 공통적으로 사용되는 기초 데이터를 관리합니다.

#### 1.1. `Company` (회사 정보)

시스템을 사용하는 회사 정보를 관리합니다.

| 필드명      | 데이터 타입 | 설명       | 제약 조건               |
| :---------- | :---------- | :--------- | :---------------------- |
| companyId   | CHAR(5)     | 회사 ID    | PK                      |
| companyName | VARCHAR(100) | 회사명     | NOT NULL                |
| note        | TEXT        | 비고       |                         |
| createBy    | CHAR(5)     | 생성자 ID  | FK (`User`)             |
| createDate  | DATETIME    | 생성일시   |                         |
| updateBy    | CHAR(5)     | 수정자 ID  | FK (`User`)             |
| updateDate  | DATETIME    | 수정일시   |                         |
| deleteMark  | CHAR(1)     | 삭제 마크  | NOT NULL, DEFAULT 'N'   |

#### 1.2. `Site` (사업장/현장 정보)

각 회사의 사업장 또는 현장 정보를 관리합니다.

| 필드명      | 데이터 타입 | 설명       | 제약 조건               |
| :---------- | :---------- | :--------- | :---------------------- |
| companyId   | CHAR(5)     | 회사 ID    | PK, FK (`Company`)      |
| siteId      | CHAR(5)     | 사업장 ID  | PK                      |
| siteName    | VARCHAR(100) | 사업장명   | NOT NULL                |
| address     | VARCHAR(255) | 주소       |                         |
| note        | TEXT        | 비고       |                         |
| createBy    | CHAR(5)     | 생성자 ID  | FK (`User`)             |
| createDate  | DATETIME    | 생성일시   |                         |
| updateBy    | CHAR(5)     | 수정자 ID  | FK (`User`)             |
| updateDate  | DATETIME    | 수정일시   |                         |
| deleteMark  | CHAR(1)     | 삭제 마크  | NOT NULL, DEFAULT 'N'   |

#### 1.3. `Dept` (부서 정보)

회사 내 부서 정보를 관리합니다.

| 필드명       | 데이터 타입 | 설명       | 제약 조건               |
| :----------- | :---------- | :--------- | :---------------------- |
| companyId    | CHAR(5)     | 회사 ID    | PK, FK (`Company`)      |
| deptId       | CHAR(5)     | 부서 ID    | PK                      |
| deptName     | VARCHAR(100) | 부서명     | NOT NULL                |
| parentDeptId | CHAR(5)     | 상위 부서 ID | FK (`Dept` 자기참조)    |
| note         | TEXT        | 비고       |                         |
| createBy     | CHAR(5)     | 생성자 ID  | FK (`User`)             |
| createDate   | DATETIME    | 생성일시   |                         |
| updateBy     | CHAR(5)     | 수정자 ID  | FK (`User`)             |
| updateDate   | DATETIME    | 수정일시   |                         |
| deleteMark   | CHAR(1)     | 삭제 마크  | NOT NULL, DEFAULT 'N'   |

#### 1.4. `User` (사용자 정보)

시스템 사용자 정보를 관리합니다.

| 필드명      | 데이터 타입 | 설명                                     | 제약 조건               |
| :---------- | :---------- | :--------------------------------------- | :---------------------- |
| companyId   | CHAR(5)     | 회사 ID                                  | PK, FK (`Company`)      |
| userId      | CHAR(5)     | 사용자 ID                                | PK                      |
| userName    | VARCHAR(50) | 사용자명                                  | NOT NULL                |
| password    | VARCHAR(255) | 비밀번호                                  | NOT NULL                |
| email       | VARCHAR(100) | 이메일                                    | UNIQUE                  |
| phoneNumber | VARCHAR(20) | 전화번호                                  |                         |
| deptId      | CHAR(5)     | 부서 ID                                  | FK (`Dept`)             |
| position    | CHAR(5)     | 직위 (Code 참조)                           |                         |
| userRole    | CHAR(5)     | 사용자 권한 (예: ADMIN, USER, Code 참조) | NOT NULL, DEFAULT 'USER' |
| note        | TEXT        | 비고                                      |                         |
| createBy    | CHAR(5)     | 생성자 ID                                | FK (`User`)             |
| createDate  | DATETIME    | 생성일시                                  |                         |
| updateBy    | CHAR(5)     | 수정자 ID                                | FK (`User`)             |
| updateDate  | DATETIME    | 수정일시                                  |                         |
| deleteMark  | CHAR(1)     | 삭제 마크                                  | NOT NULL, DEFAULT 'N'   |

#### 1.5. `SiteAccess` (사용자-사업장 접근 권한)

사용자가 접근할 수 있는 사업장 정보를 관리합니다.

| 필드명      | 데이터 타입 | 설명                                     | 제약 조건               |
| :---------- | :---------- | :--------------------------------------- | :---------------------- |
| companyId   | CHAR(5)     | 회사 ID                                  | PK, FK (`Company`)      |
| userId      | CHAR(5)     | 사용자 ID                                | PK, FK (`User`)         |
| siteId      | CHAR(5)     | 사업장 ID                                | PK, FK (`Site`)         |
| accessLevel | CHAR(5)     | 접근 레벨 (Code 참조)                     | NOT NULL                |
| note        | TEXT        | 비고                                      |                         |
| createBy    | CHAR(5)     | 생성자 ID                                | FK (`User`)             |
| createDate  | DATETIME    | 생성일시                                  |                         |
| updateBy    | CHAR(5)     | 수정자 ID                                | FK (`User`)             |
| updateDate  | DATETIME    | 수정일시                                  |                         |
| deleteMark  | CHAR(1)     | 삭제 마크                                  | NOT NULL, DEFAULT 'N'   |

#### 1.6. `RoleAuth` (역할-권한 관리)

사용자 역할에 따른 메뉴/기능 접근 권한을 관리합니다.

| 필드명      | 데이터 타입 | 설명                                   | 제약 조건               |
| :---------- | :---------- | :------------------------------------- | :---------------------- |
| companyId   | CHAR(5)     | 회사 ID                                | PK, FK (`Company`)      |
| roleId      | CHAR(5)     | 역할 ID (Code 참조)                     | PK                      |
| authId      | CHAR(10)    | 권한 ID (메뉴/기능 ID)                   | PK                      |
| accessYN    | CHAR(1)     | 접근 가능 여부 ('Y'/'N')               | NOT NULL, DEFAULT 'N'   |
| note        | TEXT        | 비고                                    |                         |
| createBy    | CHAR(5)     | 생성자 ID                                | FK (`User`)             |
| createDate  | DATETIME    | 생성일시                                  |                         |
| updateBy    | CHAR(5)     | 수정자 ID                                | FK (`User`)             |
| updateDate  | DATETIME    | 수정일시                                  |                         |
| deleteMark  | CHAR(1)     | 삭제 마크                                  | NOT NULL, DEFAULT 'N'   |

#### 1.7. `Code` (공통 코드)

공통 코드 그룹 및 각 그룹에 속하는 상세 코드 정보를 통합하여 관리합니다. `codeId`가 `0000000000`인 레코드는 해당 `codeGroup`의 마스터 정보로 간주합니다. (예: 'PLTTP' 그룹의 마스터 레코드는 `codeGroup='PLTTP'`, `codeId='0000000000'` 이고 `codeName='설비유형'`이 됨)

| 필드명     | 데이터 타입 | 설명                                         | 제약 조건                     |
| :--------- | :---------- | :------------------------------------------- | :------------------------------ |
| companyId  | CHAR(5)     | 회사 ID                                      | PK, FK (`Company`)            |
| codeGroup  | CHAR(5)     | 코드 그룹 ID                                 | PK                            |
| codeId     | CHAR(10)    | 코드 항목 ID (그룹 내 유일)                   | PK                            |
| codeName   | VARCHAR(100) | 코드명 (그룹명 또는 항목명)                   | NOT NULL                      |
| sortOrder  | INT         | 정렬 순서                                    |                               |
| note       | TEXT        | 비고                                         |                               |
| createBy   | CHAR(5)     | 생성자 ID                                    | FK (`User`)                   |
| createDate | DATETIME    | 생성일시                                     |                               |
| updateBy   | CHAR(5)     | 수정자 ID                                    | FK (`User`)                   |
| updateDate | DATETIME    | 수정일시                                     |                               |
| deleteMark | CHAR(1)     | 삭제 마크                                    | NOT NULL, DEFAULT 'N'         |

#### 1.8. `File` (첨부 파일)

첨부파일의 메타데이터를 관리합니다. `fileGroupId`는 파일을 첨부한 마스터 테이블의 ID (예: `plantId`, `inventoryId`, `workOrderId`, `inspectionId`, `memoId`)를 직접 사용하며, 실제 파일 경로는 `fileId`를 기준으로 생성됩니다.

| 필드명        | 데이터 타입 | 설명                                         | 제약 조건                     |
| :---------- | :---------- | :------------------------------------------- | :------------------------------ |
| companyId     | CHAR(5)     | 회사 ID                                      | PK, FK (`Company`)            |
| fileGroupId   | CHAR(10)    | 파일 그룹 ID (참조하는 마스터 테이블의 ID) | PK (해당 마스터 테이블의 PK와 동일한 값) |
| fileId        | CHAR(10)    | 파일 ID (그룹 내 유일한 순번, 예: F000000001) | PK                            |
| fileName      | VARCHAR(255) | 원본 파일명                                  | NOT NULL                      |
| filePath      | VARCHAR(500) | 파일 저장 경로 (물리적 경로, 예: `/uploads/C0001/PLT2406210001/F000000001_도면.pdf`) | NOT NULL (filePath는 fileId를 포함하여 생성됨) |
| fileSize      | BIGINT      | 파일 크기 (bytes)                           |                               |
| fileExtension | VARCHAR(10) | 파일 확장자                                  |                               |
| note          | TEXT        | 비고                                         |                               |
| createBy      | CHAR(5)     | 생성자 ID                                    | FK (`User`)                   |
| createDate    | DATETIME    | 생성일시 (== 파일 업로드 일시)              |                               |
| updateBy      | CHAR(5)     | 수정자 ID                                    | FK (`User`)                   |
| updateDate    | DATETIME    | 수정일시                                     |                               |
| deleteMark    | CHAR(1)     | 삭제 마크                                    | NOT NULL, DEFAULT 'N'         |

---

### 2. 설비 관리 (`plant` 모듈)

#### 2.1. `Plant` (설비 마스터)

설비의 기본 정보 및 현재 상태를 관리하는 테이블입니다.

| 필드명              | 데이터 타입    | 설명                                     | 제약 조건                   |
| :------------------ | :------------- | :--------------------------------------- | :-------------------------- |
| companyId           | CHAR(5)        | 회사 ID                                  | PK, FK (`Company`)          |
| plantId             | CHAR(10)       | 설비 ID (자동 생성, 예: PLT2406210001)   | PK                          |
| siteId              | CHAR(5)        | 사업장 ID                                | FK (`Site`)                 |
| funcId              | CHAR(5)        | 기능 그룹 코드 (`Code` 참조: `codeGroup` = 'FUNC_GROUP') |                             |
| plantName           | VARCHAR(100)   | 설비명                                   | NOT NULL                    |
| plantType           | CHAR(5)        | 설비 타입 (`Code` 참조: `codeGroup` = 'PLT_TYPE') | NOT NULL                    |
| respDept            | CHAR(5)        | 담당 부서 ID                             | FK (`Dept`)                 |
| assetType           | CHAR(5)        | 자산 유형 (`Code` 참조: `codeGroup` = 'ASSET_TYPE') |                             |
| installDate         | DATE           | 설치 일자                                |                             |
| manufacturer        | VARCHAR(100)   | 제조사                                   |                             |
| manufacturerModel   | VARCHAR(100)   | 모델명                                   |                             |
| manufacturerSN      | VARCHAR(100)   | 시리얼 넘버                              |                             |
| manufacturerSpec    | VARCHAR(100)   | 규격                                     |                             |
| acquitionValue      | DECIMAL(15,2)  | 취득 가액                                |                             |
| depreMethod         | CHAR(5)        | 감가상각 방법 (`Code` 참조: `codeGroup` = 'DEPRE_METHOD') |                             |
| residualValue       | DECIMAL(15,2)  | 잔존 가액                                |                             |
| location            | VARCHAR(200)   | 설치 위치                                |                             |
| operationStatus     | CHAR(5)        | 가동 상태 (`Code` 참조: `codeGroup` = 'PLT_STATUS') | NOT NULL, DEFAULT 'IDLE' |
| inspectionYN        | CHAR(1)        | 점검 필요 여부 ('Y'/'N')                 | NOT NULL, DEFAULT 'N'       |
| psmYN               | CHAR(1)        | PSM(공정안전관리) 대상 여부 ('Y'/'N')    | NOT NULL, DEFAULT 'N'       |
| tagYN               | CHAR(1)        | Tag 부착 여부 ('Y'/'N')                  | NOT NULL, DEFAULT 'N'       |
| lastMaintenanceDate | DATETIME       | 최근 보전 일시                           |                             |
| nextMaintenanceDate | DATETIME       | 다음 보전 예정 일시                      |                             |
| fileGroupId         | CHAR(10)       | 첨부 파일 그룹 ID                        | FK (`File`)                 |
| note                | TEXT           | 비고 (설비 특이사항 등)                  |                             |
| createBy            | CHAR(5)        | 생성자 ID (FK `User`)                   |                             |
| createDate          | DATETIME       | 생성일시                                 |                             |
| updateBy            | CHAR(5)        | 수정자 ID (FK `User`)                   |                             |
| updateDate          | DATETIME       | 수정일시                                 |                             |
| deleteMark          | CHAR(1)        | 삭제 마크 ('Y' 또는 'N')                 | NOT NULL, DEFAULT 'N'       |

---

### 3. 재고 관리 (`inventory` 모듈)

#### 3.1. `Inventory` (재고 마스터)

재고의 기본 정보, 제조사/모델/시리얼/규격 정보, 그리고 현재 수량과 가치를 관리하는 테이블입니다.

| 필드명             | 데이터 타입    | 설명                                     | 제약 조건                      |
| :----------------- | :------------- | :--------------------------------------- | :----------------------------- |
| companyId          | CHAR(5)        | 회사 ID                                  | PK, FK (`Company`)            |
| inventoryId        | CHAR(10)       | 재고 ID (자동 생성, 예: INV2406210001)   | PK                             |
| siteId             | CHAR(5)        | 사업장 ID                                | FK (`Site`)                  |
| inventoryName      | VARCHAR(100)   | 재고명                                   | NOT NULL                       |
| inventoryType      | CHAR(5)        | 재고 타입 (`Code` 참조: `codeGroup` = 'INV_TYPE') | NOT NULL                     |
| respDept           | CHAR(5)        | 담당 부서 ID                             | FK (`Dept`)                  |
| assetType          | CHAR(5)        | 자산 유형 (`Code` 참조: `codeGroup` = 'ASSET_TYPE') |                             |
| manufacturer       | VARCHAR(100)   | 제조사                                   |                                |
| manufacturerModel  | VARCHAR(100)   | 모델 넘버                                |                                |
| manufacturerSN     | VARCHAR(100)   | 시리얼 번호                              |                                |
| manufacturerSpec   | VARCHAR(100)   | 규격 (예: 21mm, 크롬바나듐)               |                                |
| unit               | CHAR(5)        | 단위 (`Code` 참조: `codeGroup` = 'UNIT') |                                |
| location           | VARCHAR(200)   | 보관 위치 (예: 창고 A-2 선반 3)         |                                |
| currentQty         | DECIMAL(15,2)  | 현재 재고 수량                           | NOT NULL, DEFAULT 0.00         |
| currentValue       | DECIMAL(15,2)  | 현재 재고 가치 (currentQty * 기준 구매단가) | NOT NULL, DEFAULT 0.00         |
| fileGroupId        | CHAR(10)       | 첨부 파일 그룹 ID                        | FK (`File`)                  |
| note               | TEXT           | 비고 (재고 특이사항 등)                  |                                |
| createBy           | CHAR(5)        | 생성자 ID (FK `User`)                   |                                |
| createDate         | DATETIME       | 생성일시                                 |                                |
| updateBy           | CHAR(5)        | 수정자 ID (FK `User`)                   |                                |
| updateDate         | DATETIME       | 수정일시                                 |                                |
| deleteMark         | CHAR(1)        | 삭제 마크 ('Y' 또는 'N')                 | NOT NULL, DEFAULT 'N'          |

#### 3.2. `InventoryHistory` (재고 이력)

재고의 모든 입출고 및 조정 등 수량/가치 변동 이력을 기록하는 테이블입니다. `transactionQty`와 `transactionPrice`는 트랜잭션 발생 시점의 값이며, `Inventory`의 `currentQty`와 `currentValue`를 업데이트하는 기준이 됩니다.

| 필드명           | 데이터 타입    | 설명                                       | 제약 조건                               |
| :--------------- | :------------- | :----------------------------------------- | :-------------------------------------- |
| companyId        | CHAR(5)        | 회사 ID                                    | PK, FK (`Company`)                      |
| historyId        | CHAR(15)       | 재고 이력 ID (자동 생성, 예: HSY24062100001) | PK                                      |
| inventoryId      | CHAR(10)       | 재고 ID                                    | FK (`Inventory`)                        |
| transactionType  | CHAR(5)        | 트랜잭션 유형 (IN: 입고, OUT: 출고, ADJ: 재고조정, MOVE: 이동 등, `Code` 참조: `codeGroup` = 'INV_TRANS_TYPE') | NOT NULL                                |
| transactionDate  | DATETIME       | 트랜잭션 발생 일시                         | NOT NULL                                |
| transactionQty   | DECIMAL(15,2)  | 변동 수량 (항상 양수 값으로 기록)          | NOT NULL, `transactionType`에 따라 `currentQty`에 가감 |
| transactionPrice | DECIMAL(15,2)  | 단가 (트랜잭션 시점의 단위 가격)             | NOT NULL                                |
| transactionValue | DECIMAL(15,2)  | 변동 금액 (transactionQty * transactionPrice) | NOT NULL                                |
| note             | TEXT           | 비고 (입출고 사유, 조정 내역 등)           |                                         |
| createBy         | CHAR(5)        | 생성자 ID (FK `User`)                     |                                         |
| createDate       | DATETIME       | 생성일시                                   |                                         |
| updateBy         | CHAR(5)        | 수정자 ID (FK `User`)                     |                                         |
| updateDate       | DATETIME       | 수정일시                                   |                                         |
| deleteMark       | CHAR(1)        | 삭제 마크 ('Y' 또는 'N')                   | NOT NULL, DEFAULT 'N'                   |

---

### 4. 예방 점검 관리 (`inspection` 모듈)

#### 4.1. `Inspection` (점검 실적)

실제 설비 점검 수행 결과를 기록하고 관리하는 테이블입니다. 별도의 계획 없이 실적만 관리합니다.

| 필드명              | 데이터 타입    | 설명                                     | 제약 조건                   |
| :------------------ | :------------- | :--------------------------------------- | :-------------------------- |
| companyId           | CHAR(5)        | 회사 ID                                  | PK, FK (`Company`)          |
| inspectionId        | CHAR(10)       | 점검 실적 ID (자동 생성, 예: INSRLT240001) | PK                          |
| siteId              | CHAR(5)        | 사업장 ID                                | FK (`Site`)                 |
| plantId             | CHAR(10)       | 대상 설비 ID                             | FK (`Plant`)                |
| inspectionName      | VARCHAR(100)   | 점검명                                   | NOT NULL                    |
| inspectionDate      | DATE           | 실제 점검 수행일                         | NOT NULL                    |
| performDept         | CHAR(5)        | 점검 수행 부서 ID                        | FK (`Dept`)                 |
| inspectorUserId     | CHAR(5)        | 점검 수행자 ID                           | FK (`User`)                 |
| inspectionResultOverall | CHAR(5)    | 종합 점검 결과 (`Code` 참조: `codeGroup` = 'INSP_RESULT') | NOT NULL                    |
| problemDescription  | TEXT           | 발견된 문제점                            |                             |
| actionTaken         | TEXT           | 취해진 조치 사항                         |                             |
| fileGroupId         | CHAR(10)       | 첨부 파일 그룹 ID                        | FK (`File`)                 |
| note                | TEXT           | 비고                                     |                             |
| createBy            | CHAR(5)        | 생성자 ID (FK `User`)                   |                             |
| createDate          | DATETIME       | 생성일시                                 |                             |
| updateBy            | CHAR(5)        | 수정자 ID (FK `User`)                   |                             |
| updateDate          | DATETIME       | 수정일시                                 |                             |
| deleteMark          | CHAR(1)        | 삭제 마크 ('Y' 또는 'N')                 | NOT NULL, DEFAULT 'N'       |

#### 4.2. `InspectionItem` (점검 실적 항목)

각 점검 실적에 대한 개별 점검 항목별 결과를 기록합니다.

| 필드명              | 데이터 타입    | 설명                                     | 제약 조건                   |
| :------------------ | :------------- | :--------------------------------------- | :-------------------------- |
| companyId           | CHAR(5)        | 회사 ID                                  | PK, FK (`Company`)          |
| inspectionId        | CHAR(10)       | 점검 실적 ID                             | PK, FK (`Inspection`)       |
| itemId              | CHAR(10)       | 점검 항목 ID (실적 내 유일 순번)         | PK                          |
| itemName            | VARCHAR(100)   | 점검 항목명                              | NOT NULL                    |
| method              | VARCHAR(100)   | 점검 방법                                |                             |
| lowerValue          | VARCHAR(100)   | 하한 값 (텍스트 또는 숫자)               |                             |
| upperValue          | VARCHAR(100)   | 상한 값 (텍스트 또는 숫자)               |                             |
| standardValue       | VARCHAR(100)   | 표준 값 (텍스트 또는 숫자)               |                             |
| actualValue         | VARCHAR(100)   | 실제 결과 값                             |                             |
| note                | TEXT           | 항목별 비고                              |                             |
| createBy            | CHAR(5)        | 생성자 ID (FK `User`)                   |                             |
| createDate          | DATETIME       | 생성일시                                 |                             |
| updateBy            | CHAR(5)        | 수정자 ID (FK `User`)                   |                             |
| updateDate          | DATETIME       | 수정일시                                 |                             |
| deleteMark          | CHAR(1)        | 삭제 마크 ('Y' 또는 'N')                 | NOT NULL, DEFAULT 'N'       |

---

### 5. 작업 오더 관리 (`workorder` 모듈)

#### 5.1. `WorkOrder` (작업 오더 마스터)

설비 고장 수리, 예방 보전 작업 등을 위한 작업 오더를 관리합니다.

| 필드명         | 데이터 타입    | 설명                                         | 제약 조건                   |
| :------------- | :------------- | :------------------------------------------- | :-------------------------- |
| companyId      | CHAR(5)        | 회사 ID                                      | PK, FK (`Company`)          |
| workOrderId    | CHAR(10)       | 작업 오더 ID (자동 생성, 예: WO2406210001)   | PK                          |
| siteId         | CHAR(5)        | 사업장 ID                                    | FK (`Site`)                 |
| plantId        | CHAR(10)       | 대상 설비 ID                                 | FK (`Plant`)                |
| workorderName  | VARCHAR(100)   | 작업 오더 명칭                               | NOT NULL                    |
| workOrderType  | CHAR(5)        | 작업 오더 유형 (`Code` 참조: `codeGroup` = 'WO_TYPE'. 실제 저장되는 값은 코드 ID(예: BKD, PREV)이며, 예시는 코드명이므로 더 길 수 있습니다.) | NOT NULL                    |
| scheduleDate   | DATE           | 계획 작업일                                  |                             |
| scheduleMM     | DECIMAL(15,2)  | 계획 맨먼스 (Man-Month)                      |                             |
| scheduleCost   | DECIMAL(15,2)  | 계획 비용                                    |                             |
| scheduleHSE    | TEXT           | 계획 안전보건 사항                           |                             |
| executeDate    | DATE           | 실제 작업일                                  |                             |
| executeMM      | DECIMAL(15,2)  | 실제 맨먼스                                  |                             |
| executeCost    | DECIMAL(15,2)  | 실제 비용                                    |                             |
| executeHSE     | TEXT           | 실제 안전보건 사항                           |                             |
| status         | CHAR(5)        | 작업 오더 상태 (`Code` 참조: `codeGroup` = 'WO_STATUS'. 실제 저장되는 값은 코드 ID(예: REQ, INP)이며, 예시는 코드명이므로 더 길 수 있습니다.) | NOT NULL, DEFAULT 'REQUEST' |
| fileGroupId    | CHAR(10)       | 첨부 파일 그룹 ID                            | FK (`File`)                 |
| note           | TEXT           | 비고                                         |                             |
| createBy       | CHAR(5)        | 생성자 ID                                    | FK (`User`)                 |
| createDate     | DATETIME       | 생성일시                                     |                             |
| updateBy       | CHAR(5)        | 수정자 ID                                    | FK (`User`)                 |
| updateDate     | DATETIME       | 수정일시                                     |                             |
| deleteMark     | CHAR(1)        | 삭제 마크 ('Y' 또는 'N')                     | NOT NULL, DEFAULT 'N'       |

#### 5.2. `WorkOrderItem` (작업 오더 항목)

각 작업 오더에 포함되는 세부 작업 항목을 관리합니다.

| 필드명         | 데이터 타입 | 설명       | 제약 조건               |
| :------------- | :---------- | :--------- | :---------------------- |
| companyId      | CHAR(5)     | 회사 ID    | PK, FK (`Company`)      |
| workOrderId    | CHAR(10)    | 작업 오더 ID | PK, FK (`WorkOrder`)    |
| itemId         | CHAR(10)    | 항목 ID    | PK                      |
| itemName       | VARCHAR(100) | 항목명     | NOT NULL                |
| method         | VARCHAR(100) | 작업 방법  |                         |
| result         | VARCHAR(100) | 작업 결과  |                         |
| note           | TEXT        | 비고       |                         |
| createBy       | CHAR(5)     | 생성자 ID  | FK (`User`)             |
| createDate     | DATETIME    | 생성일시   |                         |
| updateBy       | CHAR(5)     | 수정자 ID  | FK (`User`)             |
| updateDate     | DATETIME    | 수정일시   |                         |
| deleteMark     | CHAR(1)     | 삭제 마크  | NOT NULL, DEFAULT 'N'   |

---

### 6. 작업 메모 (`memo` 모듈)

#### 6.1. `Memo` (메모)

특정 엔티티(설비, 재고, 작업 오더 등)에 연결되는 메모를 관리합니다. 일반 게시판처럼 활용할 수도 있습니다.

| 필드명        | 데이터 타입 | 설명                                        | 제약 조건                   |
| :---------- | :---------- | :------------------------------------------ | :-------------------------- |
| companyId     | CHAR(5)     | 회사 ID                                     | PK, FK (`Company`)          |
| memoId        | CHAR(10)    | 메모 ID (자동 생성, 예: MEMO2406210001)     | PK                          |
| targetType    | CHAR(5)     | 메모 대상 유형 (예: PLANT, INVENTORY, WORKORD, `Code` 참조: `codeGroup` = 'MEMO_TARGET') | NOT NULL                    |
| targetId      | CHAR(10)    | 메모 대상 ID (targetType에 따른 FK)         | NOT NULL                    |
| memoName      | VARCHAR(200) | 메모 제목                                   | NOT NULL                    |
| memoContent   | TEXT        | 메모 내용                                   | NOT NULL                    |
| fileGroupId   | CHAR(10)    | 첨부 파일 그룹 ID                            | FK (`File`)                 |
| note          | TEXT        | 비고                                        |                             |
| createBy      | CHAR(5)     | 생성자 ID                                   | FK (`User`)                 |
| createDate    | DATETIME    | 생성일시                                    |                             |
| updateBy      | CHAR(5)     | 수정자 ID                                   | FK (`User`)                 |
| updateDate    | DATETIME    | 수정일시                                    |                             |
| deleteMark    | CHAR(1)     | 삭제 마크                                   | NOT NULL, DEFAULT 'N'       |

#### 6.2. `MemoComment` (메모 댓글)

각 메모에 달리는 댓글 정보를 관리합니다.

| 필드명        | 데이터 타입 | 설명                                        | 제약 조건                   |
| :---------- | :---------- | :------------------------------------------ | :-------------------------- |
| companyId     | CHAR(5)     | 회사 ID                                     | PK, FK (`Company`)          |
| commentId     | CHAR(10)    | 댓글 ID (자동 생성)                         | PK                          |
| memoId        | CHAR(10)    | 메모 ID                                     | FK (`Memo`)                 |
| commentContent| TEXT        | 댓글 내용                                   | NOT NULL                    |
| note          | TEXT        | 비고                                        |                             |
| createBy      | CHAR(5)     | 생성자 ID                                   | FK (`User`)                 |
| createDate    | DATETIME    | 생성일시                                    |                             |
| updateBy      | CHAR(5)     | 수정자 ID                                   | FK (`User`)                 |
| updateDate    | DATETIME    | 수정일시                                    |                             |
| deleteMark    | CHAR(1)     | 삭제 마크                                   | NOT NULL, DEFAULT 'N'       |