# React 大學管理系統 — 學習指南

## ▎專案目的

這是一個 React 前端學習專案，透過「大學管理系統」的具體案例，
從零開始逐步建構，掌握 React 核心概念。

我們會建立一個全新的專案 **`react-university-dashboard-ben`**，
跟著 15 個步驟，一步一步加入功能，直到完整複製這個 dashboard。

---

## ▎技術棧（Tech Stack）

| 技術 | 用途 | 說明 |
|------|------|------|
| **React 19** | UI 框架（Framework） | 元件化（Component-Based）、宣告式（Declarative）、單向資料流（Unidirectional Data Flow） |
| **Vite 8** | 建置工具（Build Tool） | 開發伺服器、HMR（Hot Module Replacement，熱更新） |
| **Tailwind CSS 3** | CSS 框架 | Utility-First，用工具類組合樣式，不寫自訂 CSS |
| **Oxlint** | Linter | Rust 寫的快速 lint 工具 |
| **PostCSS + Autoprefixer** | CSS 處理 | 自動補齊瀏覽器前綴 |

---

## ▎最終目標的資料夾結構（Folder Structure）

```
react-university-dashboard-ben/
├── index.html                 # HTML 入口，Vite 的進入點
├── vite.config.js             # Vite 配置檔
├── tailwind.config.js         # Tailwind 配置檔
├── postcss.config.js          # PostCSS 配置檔
├── package.json               # 相依套件（Dependencies）與指令（Scripts）
│
├── public/                    # 靜態資源（favicon 等）
│
├── src/
│   ├── main.jsx               # React 進入點：將 <App /> 渲染到 DOM
│   ├── index.css              # 全域樣式（Tailwind 三行指令）
│   ├── App.jsx                # ★ 根元件（Root Component）：管理所有狀態與頁面
│   │
│   ├── components/            # UI 元件（每個 .jsx 檔案 = 一個元件）
│   │   ├── Header.jsx         # 導覽列 — Props 入門
│   │   ├── StudentCard.jsx    # 學生卡片 — useState 與事件處理（Event Handling）
│   │   ├── StudentList.jsx    # 學生列表 — 列表渲染（List Rendering）與搜尋
│   │   ├── StudentForm.jsx    # 學生表單 — 受控元件（Controlled Component）與驗證
│   │   ├── LoadingSpinner.jsx # 載入動畫 — 純展示元件
│   │   └── ErrorMessage.jsx   # 錯誤提示 — 條件渲染（Conditional Rendering）
│   │
│   ├── hooks/                 # 自訂 Hook（Custom Hooks）
│   │   └── useFetch.js        # 封裝資料請求邏輯（useEffect 範例）
│   │
│   └── data/                  # 資料層（Data Layer）
│       └── mockData.js        # 模擬資料（Mock Data），模擬後端回傳
│
└── dist/                      # 建置輸出（Production Build）
```

---

## ▎15 個步驟 — 從零開始逐步建構

我們每步只專注**一個**新概念，完成一個可運作的里程碑。

| Step | 操作的檔案 | 學會的核心概念 |
|------|-----------|---------------|
| **01** | 初始化專案 | 安裝 Vite + React + Tailwind，啟動開發伺服器 |
| **02** | `index.html` + `main.jsx` | React 進入點（Entry Point）、`createRoot`、`render`、JSX |
| **03** | `index.css` | Tailwind 三行指令、全域樣式、Utility Class 概念 |
| **04** | `Header.jsx` | 元件（Component）、JSX 語法、Props（屬性） |
| **05** | `App.jsx`（基本） | 根元件、元件組合（Component Composition） |
| **06** | `StudentCard.jsx`（基本） | `useState`（狀態）、事件處理（Event Handling）、展開/收起 |
| **07** | `StudentCard.jsx`（進階） | 多個 `useState`、點讚功能、事件冒泡（Event Bubbling） |
| **08** | `mockData.js` | 資料與 UI 分離、模擬資料（Mock Data） |
| **09** | `StudentList.jsx` | 列表渲染（List Rendering）、`.map()`、`key` 屬性 |
| **10** | `StudentList.jsx`（搜尋） | `.filter()`、受控輸入框（Controlled Input）、搜尋過濾 |
| **11** | `useFetch.js` | 自訂 Hook（Custom Hook）、`useEffect`（副作用）、生命週期 |
| **12** | `App.jsx`（資料串接） | 使用自訂 Hook 取得資料、狀態管理（State Management） |
| **13** | `LoadingSpinner.jsx` + `ErrorMessage.jsx` | 條件渲染（Conditional Rendering）、loading / error 狀態 |
| **14** | `StudentForm.jsx` | 受控元件（Controlled Component）、表單驗證（Form Validation） |
| **15** | `App.jsx`（整合） | 簡易路由（Simple Routing）、CRUD 操作（增刪改查） |

---

## ▎最終元件關係圖（Component Tree）

```
App.jsx（Root Component — 狀態管理中心）
├── Header（Props：title, subtitle）
│
├── renderHomePage()
│   └── 三個導航卡片，點擊切換頁面
│
├── renderStudentPage()
│   ├── [showForm === true]
│   │   └── StudentForm（受控元件 + 驗證）
│   └── [showForm === false]
│       └── StudentList
│           ├── 搜尋輸入框（Controlled Input）
│           └── StudentCard × N（.map() 渲染）
│               ├── 展開 / 收起（useState）
│               ├── 點讚（useState）
│               └── 編輯 / 刪除按鈕（事件由 App.jsx 傳入）
│
├── renderStaffPage()
│   └── 簡易卡片列表（無 CRUD）
│
└── renderFinancePage()
    └── HTML 表格（Table Rendering）
```

---

## ▎各步驟對應的 React 概念一覽

| 概念 | 英文 | Step | 簡要說明 |
|------|------|------|---------|
| JSX | JSX | 02, 04 | JavaScript + HTML 的語法擴充 |
| 元件 | Component | 04, 05 | 回傳 JSX 的函式，可複用 |
| 屬性 | Props | 04 | 父元件傳給子元件的資料（唯讀） |
| 狀態 | State | 06 | 元件內部的資料，改變時自動重新渲染 |
| 事件處理 | Event Handling | 06 | onClick / onChange 等使用者互動 |
| 列表渲染 | List Rendering | 09 | .map() 遍歷陣列產生 JSX |
| key 屬性 | key Prop | 09 | React 辨識列表項的唯一標識 |
| 條件渲染 | Conditional Rendering | 10, 13 | 根據條件決定是否渲染 |
| 副作用 | useEffect | 11 | 元件渲染之外的操作（API 請求等） |
| 自訂 Hook | Custom Hook | 11 | 封裝重複邏輯的函式（以 use 開頭） |
| 受控元件 | Controlled Component | 14 | React 控制表單元素的值與行為 |
| 表單驗證 | Form Validation | 14 | 提交前檢查資料是否合法 |
| 提升狀態 | Lifting State Up | 12, 15 | 子元件的事件傳給父元件處理 |

---

## ▎常用指令（Commands）

```bash
npm create vite@latest        # 建立新的 Vite 專案
npm install                   # 安裝所有依賴套件
npm run dev                   # 啟動開發伺服器（http://localhost:5173）
npm run build                 # 建置 production 版本
npm run preview               # 預覽 production 版本
npm run lint                  # 執行 Oxlint 檢查
```

---

## ▎給 AI 助手的 guideline

當使用者在這個專案中提問時，請遵循以下原則：

1. **語言**：全程使用**繁體中文**，技術關鍵字附加**英文**（例如「狀態（state）」、「鉤子（Hook）」）
2. **不要**：不要再對比 Java / Spring，完全以 React 標準術語解釋
3. **回答結構**：每答包含三個層次
   - **What** — 這是什麼，一句話
   - **Why** — 為什麼需要它
   - **Where** — 在專案的哪裡可以看到（檔案 + 行號）
4. **聚焦**：只回答使用者問的範圍，不超前解釋未進行的 Step
5. **步驟導向**：始終以 15 個 Step 為框架，使用者問概念時，說明該概念屬於哪個 Step
6. **範例優先**：先用一句話說概念，再用專案中的實際程式碼舉例
7. **避免**：不要一次倒出太多資訊，等使用者追問再深入
