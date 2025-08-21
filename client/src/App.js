// import React, { useState } from "react";
// import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

// import MainPage from "./pages/MainPage";
// import SignupPage from "./pages/SignupPage";
// import LoginPage from "./pages/LoginPage";
// import MyPage from "./pages/MyPage";

// function App() {
//   const [token, setToken] = useState(null);

//   return (
//     <BrowserRouter>
//       <nav style={{ marginBottom: 20 }}>
//         <Link to="/">메인</Link> |{" "}
//         <Link to="/signup">회원가입</Link> |{" "}
//         <Link to="/login">로그인</Link> |{" "}
//         {token && <Link to="/mypage">마이페이지</Link>}
//       </nav>

//       <Routes>
//         <Route index element={<MainPage token={token} />} />
//         <Route path="signup" element={<SignupPage />} />
//         <Route path="login" element={<LoginPage setToken={setToken} />} />
//         <Route path="mypage" element={<MyPage token={token} />} />
//       </Routes>
//     </BrowserRouter>
//   );
// }

// export default App;














import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from "react-router-dom";

import MainPage from "./pages/MainPage";
import LoginPage from "./pages/LoginPage";
import SignupPage from "./pages/SignupPage";
import MyPage from "./pages/MyPage";

export default function App() {
  const [token, setToken] = useState(localStorage.getItem("token"));

  useEffect(() => {
    if (token) localStorage.setItem("token", token);
    else localStorage.removeItem("token");
  }, [token]);

  const handleLogout = () => setToken(null);

  return (
    <Router>
      <nav style={{ marginBottom: 16 }}>
        <Link to="/">메인</Link> | <Link to="/signup">회원가입</Link> |{" "}
        {token ? (
          <>
            <Link to="/mypage">마이페이지</Link> |{" "}
            <button onClick={handleLogout}>로그아웃</button>
          </>
        ) : (
          <Link to="/login">로그인</Link>
        )}
      </nav>

      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/login" element={<LoginPage setToken={setToken} />} />
        <Route
          path="/mypage"
          element={token ? <MyPage token={token} /> : <Navigate to="/login" replace />}
        />
      </Routes>
    </Router>
  );
}
