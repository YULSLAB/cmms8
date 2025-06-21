-----

## CMMS 프로젝트 가이드

### 프로젝트 개요

이 문서는 CMMS(Computerized Maintenance Management System) 개발을 위한 전반적인 가이드라인을 제공합니다. 설비, 재고, 예방 보전 및 고장 수리 등의 효율적인 관리 및 추적을 목표로 하며, 시스템 아키텍처, 데이터베이스 구조, 주요 사용자 인터페이스(UI) 화면 정의를 포함합니다.

-----

### 개발 환경 및 기본 원칙

  * **JDK:** 21
  * **프레임워크:** Spring Boot (`springframework.boot` version `3.2.3`), Gradle 8.14, Groovy
  * **데이터베이스:** MariaDB (현재 개발환경: 10.6.21-MariaDB)
  * **프론트엔드:** Thymeleaf, Tailwind CSS
  * **운영 환경:** Ubuntu
  * **개발 환경:** Windows
  * **기본 원칙:**
      * 각 코드는 시작 부분에 이름, 기능 설명, 생성자, 생성일, 수정자, 수정일, 파라미터 주석을 포함합니다.
      * 각 단계별 주요 기능에 대해 간단한 설명을 포함합니다.
      * 연계되는 프로그램에 대해 파라미터의 `type`과 `name`을 명확히 확인합니다.
      * 코딩은 최대한 간결하고 명확하게 작성하여 유지보수성을 높이고 디버깅이 용이하도록 합니다.

-----

### 파일 Naming Rule

  * **Controller:** `[모듈명][도메인명]Controller.java` 또는 `[도메인명]Controller.java` (모듈 내 유일하면) 예: `PlantController.java`, `InventoryController.java`
  * **Entity:** `[도메인명].java` 예: `Plant.java`, `Inventory.java`
  * **Service:** `[도메인명]Service.java` 예: `PlantService.java`, `InventoryService.java`
  * **Repository:** `[도메인명]Repository.java` 예: `PlantRepository.java`, `InventoryRepository.java`
  * **DTO:** `[도메인명]DTO.java` 예: `PlantDTO.java`, `InventoryDTO.java`
  * **View (HTML):** `[도메인명][Suffix].html`
      * 입력/수정 화면: `[도메인명]Form.html`
      * 리스트 화면: `[도메인명]List.html`
      * 상세 화면: `[도메인명]Detail.html`

-----

### 프로젝트 디렉토리 구조 (예시)

#### Java Source (`src/main/java/com/example/cmms/`)

```
.
└── cmms/
    ├── auth/          /** 인증 및 권한 관련 모듈 */
    │   ├── controller/
    │   ├── entity/
    │   ├── service/
    │   └── repository/
    ├── common/        /** 기준 정보 및 공통 기능 모듈 */
    │   ├── controller/
    │   ├── entity/
    │   ├── service/
    │   └── repository/
    ├── plant/         /** 설비 관리 모듈 */
    │   ├── controller/
    │   ├── entity/
    │   ├── service/
    │   └── repository/
    ├── inventory/     /** 재고 관리 모듈 */
    │   ├── controller/
    │   ├── entity/
    │   ├── service/
    │   └── repository/
    ├── inspection/    /** 예방 점검 모듈 */
    │   ├── controller/
    │   ├── entity/
    │   ├── service/
    │   └── repository/
    ├── workorder/     /** 작업 오더 모듈 */
    │   ├── controller/
    │   ├── entity/
    │   ├── service/
    │   └── repository/
    ├── memo/          /** 작업 메모 (게시판) 모듈 */
    │   ├── controller/
    │   ├── entity/
    │   ├── service/
    │   └── repository/
    └── CmmsApplication.java /** 메인 애플리케이션 클래스 */
```

#### Web Resources (`src/main/resources/templates/`)

```
.
└── templates/
    ├── layout/        /** 공통 레이아웃 (Layout.html 등) */
    ├── auth/          /** 인증 관련 화면 */
    │   └── LoginForm.html
    ├── common/        /** 기준 정보 및 공통 기능 화면 */
    │   ├── company/
    │   │   ├── CompanyForm.html
    │   │   ├── CompanyList.html
    │   │   └── CompanyDetail.html
    │   ├── site/
    │   │   ├── SiteForm.html
    │   │   ├── SiteList.html
    │   │   └── SiteDetail.html
    │   ├── dept/
    │   │   ├── DeptForm.html
    │   │   ├── DeptList.html
    │   │   └── DeptDetail.html
    │   ├── user/
    │   │   ├── UserForm.html
    │   │   ├── UserList.html
    │   │   └── UserDetail.html
    │   ├── code/
    │   │   ├── CodeForm.html      /** Master와 Item 통합 */
    │   │   ├── CodeList.html      /** Master와 Item 통합 */
    │   │   └── CodeDetail.html
    │   └── file/      /** 파일 관련 화면 (모달, 팝업 등으로 사용될 수 있음) */
    │       └── FileUploadModal.html
    ├── plant/         /** 설비 관리 화면 */
    │   ├── PlantForm.html
    │   ├── PlantList.html
    │   └── PlantDetail.html
    ├── inventory/     /** 재고 관리 화면 */
    │   ├── InventoryForm.html
    │   ├── InventoryList.html
    │   ├── InventoryDetail.html
    │   ├── InventoryHistoryForm.html
    │   └── InventoryHistoryList.html
    ├── inspection/    /** 예방 점검 (실적 관리) 화면 */
    │   ├── InspectionForm.html    /** 점검 실적 등록/수정 */
    │   ├── InspectionList.html    /** 점검 실적 목록 */
    │   └── InspectionDetail.html  /** 점검 실적 상세 */
    ├── workorder/     /** 작업 오더 모듈 */
    │   ├── WorkOrderForm.html
    │   ├── WorkOrderList.html
    │   └── WorkOrderDetail.html
    ├── memo/          /** 작업 메모 (게시판) 모듈 */
    │   ├── MemoForm.html
    │   ├── MemoList.html
    │   └── MemoDetail.html
    └── dashboard.html /** 메인 대시보드 */
```

**주의:** 각 도메인 엔티티는 데이터베이스 테이블과 1:1 매핑됩니다.

-----

### 데이터베이스 구조

CMMS 시스템의 데이터베이스는 `common` 모듈의 공통 테이블 (회사, 사업장, 부서, 사용자, 공통 코드, 파일 등)과 각 기능 모듈(설비, 재고, 점검, 작업오더, 메모)의 마스터 및 이력 테이블로 구성됩니다. 상세한 필드 정의, 데이터 타입, 설명 및 제약 조건은 `cmms8_database.md` 파일을 참조하십시오.

-----

### 사용자 인터페이스 (UI) 화면 정의

#### 1\. `auth` 모듈

  * **`LoginForm.html` (로그인 화면):** 사용자 인증을 위한 로그인 페이지. 아이디/비밀번호 입력 및 로그인 버튼 제공. (`userid`와 `password` 입력 필드를 사용하고, 스프링 시큐리티 설정에서 `usernameParameter("userid")`를 통해 이를 매핑합니다.)

#### 2\. `common` 모듈 (기준 정보 및 공통 기능)

  * **`CompanyForm.html` / `CompanyList.html` / `CompanyDetail.html`:** 회사 정보의 등록/수정, 목록 조회 및 상세 보기.
  * **`SiteForm.html` / `SiteList.html` / `SiteDetail.html`:** 사업장 정보의 등록/수정, 목록 조회 및 상세 보기.
  * **`DeptForm.html` / `DeptList.html` / `DeptDetail.html`:** 부서 정보의 등록/수정, 목록 조회 및 상세 보기. 계층형 구조 표시.
  * **`UserForm.html` / `UserList.html` / `UserDetail.html`:** 사용자 정보의 등록/수정, 목록 조회 및 상세 보기. 사용자 역할, 소속 부서 등의 정보 포함.
  * **`CodeForm.html` (공통 코드 등록/수정 화면):** 코드 그룹과 코드 항목을 하나의 화면에서 관리합니다.
      * **기능:** 새로운 코드 그룹 및 상세 코드 항목을 등록하거나 기존 코드를 수정.
      * **화면 상단 가이드:** "현재 편집 중인 코드 그룹은 **\[선택된 codeGroup 명칭]** 입니다. 코드 그룹의 마스터 정보(`codeId`가 `0000000000`인 레코드)를 수정하거나, 새로운 코드 항목을 추가, 기존 항목을 수정할 수 있습니다."
      * **주요 입력 필드:** `codeGroup` (코드 그룹 ID, 신규 등록 시 입력), `codeName` (코드 그룹명 또는 항목명), `codeId` (코드 항목 ID), `sortOrder`, `note`.
      * **로직 고려사항:** `codeId`가 `0000000000` (또는 정의된 예약 값)인 경우 해당 레코드는 코드 그룹의 마스터 정보로 간주하고, 그 외의 `codeId`는 해당 그룹의 상세 항목으로 간주하여 처리합니다.
  * **`CodeList.html` (공통 코드 목록 화면):** 코드 그룹과 코드 항목을 함께 조회합니다.
      * **기능:** 코드 그룹별로 상세 코드 항목을 펼쳐서 보거나 접을 수 있는 트리 또는 아코디언 형태로 표시.
      * **검색/필터링:** 코드 그룹명, 코드명 등으로 검색 가능.
      * **관리 기능:** 각 항목에서 "수정", "삭제" 버튼 제공. "신규 코드 그룹 등록" 및 "신규 코드 항목 등록" 버튼 제공.
  * **`CodeDetail.html` (공통 코드 상세 화면):** 선택된 코드 그룹 또는 코드 항목의 상세 정보를 조회합니다.
  * **`FileForm.html` / `FileList.html`:** 첨부파일 업로드 및 관리 (개별 기능보다는 다른 모듈의 화면에서 모달, 팝업 등으로 호출되어 사용).

#### 3\. `plant` 모듈 (설비 관리)

  * **`PlantForm.html` (설비 등록/수정 화면):** 설비의 신규 등록 및 기존 설비 정보 수정.
      * **기능:** 설비 마스터 데이터 입력 및 저장.
      * **주요 입력 필드:** 기본정보[`plantId` (수정 시 조회), `plantName`, `plantType` (공통 코드), `funcId`, `respDept`, `assetType`, `installDate`, `location`], 제조사정보[`manufacturer`, `manufacturerModel`, `manufacturerSN`, `manufacturerSpec`], 재무정보[`acquitionValue`, `depreMethod`, `residualValue`], 기타[`inspectionYN`, `psmYN`, `tagYN`, `note`].
      * **조회 전용 필드:** `operationStatus`, `lastMaintenanceDate`, `nextMaintenanceDate`.
      * **첨부파일:** `Plant` 엔티티의 `plantId`를 `File` 테이블의 `fileGroupId`로 사용하여 첨부파일을 관리합니다.
  * **`PlantList.html` (설비 목록 화면):** 등록된 모든 설비 품목의 목록을 조회합니다.
      * **기능:** 설비 목록 검색, 필터링, 페이징.
      * **검색/필터링 필드:** `plantId`, `plantName`, `plantType`, `manufacturer`.
      * **데이터 표시:** `plantId`, `plantName`, `plantType`, `manufacturer`, `manufacturerModel`, `manufacturerSN`, `location`, `operationStatus`.
      * **관리 기능:** 각 목록 항목에서 "수정" (`PlantForm.html`로 이동), "상세 보기" (`PlantDetail.html`로 이동), "삭제" 기능을 제공. "신규 등록" 버튼 제공.
  * **`PlantDetail.html` (설비 상세 화면):** 특정 설비의 상세 정보를 조회합니다. 관련 점검 이력, 작업 오더 이력, 첨부파일, 메모 등을 함께 표시할 수 있습니다.

#### 4\. `inventory` 모듈 (재고 관리)

  * **`InventoryForm.html` (재고 등록/수정 화면):** 재고 품목의 신규 등록 및 기존 품목 정보 수정.
      * **기능:** 재고 마스터 데이터 입력 및 저장.
      * **주요 입력 필드:** 기본정보[`inventoryName`, `inventoryType` (공통 코드), `respDept`, `assetType`, `location`], 제조사정보[ `manufacturer`, `manufacturerModel`, `manufacturerSN`, `manufacturerSpec`],  기타[`unit`, `currentQty`, `currentValue`, `note`].
      * **조회 전용 필드:** `inventoryId` (수정 시 조회), `currentQty`, `currentValue` (이력에 의해 자동 업데이트).
      * **첨부파일:** `Inventory` 엔티티의 `inventoryId`를 `File` 테이블의 `fileGroupId`로 사용하여 첨부파일을 관리합니다.
  * **`InventoryList.html` (재고 목록 화면):** 등록된 모든 재고 품목의 목록을 조회합니다.
      * **기능:** 재고 목록 검색, 필터링, 페이징.
      * **검색/필터링 필드:** `inventoryId`, `inventoryName`, `inventoryType`, `manufacturer`.
      * **데이터 표시:** `inventoryId`, `inventoryName`, `inventoryType`, `manufacturer`, `manufacturerModel`, `manufacturerSN`, `unit`, `currentQty`, `currentValue`.
      * **관리 기능:** 각 목록 항목에서 "수정" (`InventoryForm.html`로 이동), "상세 보기" (`InventoryDetail.html`로 이동), "삭제" 기능을 제공. "신규 등록" 버튼 제공.
  * **`InventoryDetail.html` (재고 상세 화면):** 특정 재고의 상세 정보를 조회합니다. 관련 입출고 이력, 첨부파일, 메모 등을 함께 표시할 수 있습니다.
  * **`InventoryHistoryForm.html` (재고 입출고/조정 화면):** 재고 품목에 대한 입고, 출고, 재고 조정 등의 트랜잭션을 기록합니다.
      * **기능:** 재고 수량/가치 변동 기록.
      * **주요 입력 필드:** `inventoryId` (필수 선택), `transactionType` (공통 코드), `transactionDate` (필수), `transactionQty` (변동 수량, 양수 값 입력), `transactionPrice` (단가, 양수 값 입력), `note`.
      * **자동 계산:** `transactionValue` (변동 금액).
      * **데이터 연동:** 이 화면에서 기록된 트랜잭션은 `Inventory` 테이블의 `currentQty` 및 `currentValue`를 업데이트하는 로직과 연동되어야 합니다.
  * **`InventoryHistoryList.html` (재고 입출고/조정 목록 화면):** 재고의 모든 입출고 및 조정 이력을 조회합니다.
      * **기능:** 이력 검색, 필터링, 페이징.
      * **검색/필터링 필드:** `inventoryId`, `transactionType`, `transactionDate` 범위.

#### 5\. `inspection` 모듈 (예방 점검 - 실적 관리)

  * **`InspectionForm.html` (설비 점검 실적 등록/수정 화면):** 실제 설비 점검 수행 이력을 기록하고 수정합니다.
      * **기능:** 점검 수행 결과 기록.
      * **주요 입력 필드:** `inspectionId` (자동 생성), `plantId` (필수), `inspectionDate` (실제 점검일), `performDept` (수행 부서), `inspectorUserId` (필수), `inspectionResultOverall` (공통 코드), `problemDescription`, `actionTaken`, `note`, `siteId`.
      * **첨부파일:** `Inspection` 엔티티의 `inspectionId`를 `File` 테이블의 `fileGroupId`로 사용하여 첨부파일을 관리합니다.
  * **`InspectionList.html` (설비 점검 실적 목록 화면):** 실제 수행된 설비 점검 이력 목록을 조회합니다.
      * **기능:** 점검 이력 검색, 필터링, 페이징.
      * **검색/필터링 필드:** `plantId`, `inspectionDate` 범위, `inspectorUserId`, `inspectionResult`.
      * **관리 기능:** 각 목록 항목에서 "수정", "상세 보기", "삭제" 기능을 제공. "신규 등록" 버튼 제공.
  * **`InspectionDetail.html` (설비 점검 실적 상세 화면):** 특정 점검 실적의 상세 정보를 조회합니다. 관련 첨부파일, 메모 등을 함께 표시할 수 있습니다.

#### 6\. `workorder` 모듈 (작업 오더)

  * **`WorkOrderForm.html` (작업 오더 등록/수정 화면):** 설비 고장 수리, 예방 보전 작업 등을 위한 작업 오더를 등록/수정합니다.
      * **기능:** 작업 오더 정보 입력 및 저장.
      * **주요 입력 필드:** `plantId` (필수), `workorderName` (작업 오더 명칭), `workOrderType` (공통 코드), `scheduleDate`, `scheduleMM`, `scheduleCost`, `scheduleHSE`, `executeDate`, `MM`, `Cost`, `HSE`, `status` (공통 코드), `note`, `siteId`.
      * **첨부파일:** `WorkOrder` 엔티티의 `workOrderId`를 `File` 테이블의 `fileGroupId`로 사용하여 첨부파일을 관리합니다.
  * **`WorkOrderList.html` (작업 오더 목록 화면):** 등록된 모든 작업 오더 목록을 조회합니다.
      * **기능:** 작업 오더 검색, 필터링, 페이징.
      * **검색/필터링 필드:** `workOrderId`, `plantId`, `status`, `requestDate` 범위, `dueDate` 범위.
      * **관리 기능:** 각 목록 항목에서 "수정" (`WorkOrderForm.html`로 이동), "상세 보기" (`WorkOrderDetail.html`로 이동), "삭제" 기능을 제공. "신규 등록" 버튼 제공.
  * **`WorkOrderDetail.html` (작업 오더 상세 화면):** 특정 작업 오더의 상세 정보를 조회합니다. 관련 설비 정보, 사용된 재고, 첨부파일, 메모 등을 함께 표시할 수 있습니다.

#### 7\. `memo` 모듈 (작업 메모/게시판)

  * **`MemoForm.html` (메모 등록/수정 화면):** 특정 엔티티에 대한 메모를 등록하고 수정합니다.
      * **기능:** 새로운 메모 작성, 기존 메모 수정.
      * **주요 입력 필드:** `targetType` (메모 대상 유형 - `Code` 테이블에서 관리, 예: PLANT, INVENTORY, WORKORD), `targetId` (메모 대상 ID), `memoName` (메모 제목), `memoContent` (메모 내용), `createBy` (작성자).
      * **첨부파일:** `Memo` 엔티티의 `memoId`를 `File` 테이블의 `fileGroupId`로 사용하여 첨부파일을 관리합니다.
  * **`MemoList.html` (메모 목록 화면):** 특정 `targetType` 및 `targetId`에 연결된 모든 메모 목록을 조회합니다.
      * **기능:** 메모 목록 표시, 각 메모 클릭 시 `MemoDetail.html`로 이동.
      * **데이터 표시:** 제목, 작성자, 작성일시 등. 검색/필터링 기능 포함.
  * **`MemoDetail.html` (메모 상세 화면):** 특정 메모의 상세 내용을 조회하고, 댓글을 확인/작성할 수 있습니다.

-----

### 디자인 가이드

  * **프론트엔드 프레임워크:** Tailwind CSS를 사용하여 UI를 구축합니다. 이는 빠르고 유연한 스타일링을 가능하게 하며, 사용자 정의가 용이합니다.
  * **반응형 디자인:** 모든 화면은 모바일, 태블릿, 데스크톱 등 다양한 기기에서 최적의 사용자 경험을 제공하도록 반응형으로 설계합니다. Tailwind CSS의 유틸리티 클래스를 적극 활용하여 다양한 해상도에 대응합니다.
  * **일관된 UI/UX:**
      * **색상 팔레트:** 주요 액션 버튼, 경고 메시지, 배경 등에 사용되는 색상을 일관되게 정의하고 문서화합니다. (예: 주색상 - \#3B82F6 (blue-500), 보조색상 - \#6B7280 (gray-500), 성공 - \#10B981 (green-500), 경고 - \#F59E0B (yellow-500), 오류 - \#EF4444 (red-500))
      * **폰트:** 가독성을 고려하여 단일 폰트 패밀리 (`Noto Sans KR` 또는 `Pretendard`와 같은 시스템 기본 폰트)를 사용하거나, 최대 2가지 폰트 조합을 사용합니다. 폰트 크기 및 줄 간격도 일관성을 유지합니다.
      * **간격 및 정렬:** 요소 간의 패딩, 마진, 정렬 등을 Tailwind CSS의 스페이싱 스케일(spacing scale)을 사용하여 일관되게 적용하여 깔끔하고 정돈된 레이아웃을 유지합니다.
      * **아이콘:** 기능별로 통일된 아이콘 셋(예: Font Awesome, Heroicons)을 사용하여 시각적인 명확성과 직관성을 제공합니다.
      * **버튼 스타일:** 주요 액션 버튼(Primary), 보조 버튼(Secondary), 경고 버튼(Danger) 등을 명확히 구분하는 스타일을 정의합니다.
  * **사용자 피드백:** 폼 제출 시 유효성 검사 오류, 성공 메시지, 로딩 상태 등을 사용자에게 명확하게 피드백하여 혼란을 줄이고 사용성을 높입니다. (예: 입력 필드 아래 오류 메시지, 토스트 알림 등)
  * **접근성:** 기본적인 웹 접근성 가이드라인 (예: 시맨틱 HTML, 적절한 `aria` 속성 사용, 키보드 내비게이션 지원)을 준수하여 모든 사용자가 시스템을 쉽게 이용할 수 있도록 합니다.
  * **모달/팝업 사용:** 복잡한 입력이나 확인을 요구하는 경우 모달 또는 팝업을 활용하여 사용자 흐름을 방해하지 않으면서 필요한 정보를 제공합니다. 일관된 모달/팝업 스타일을 유지합니다.
  * **데이터 테이블:** 대량의 데이터를 효과적으로 표시하기 위해 정렬, 검색, 페이징 기능을 갖춘 데이터 테이블 컴포넌트를 사용합니다.