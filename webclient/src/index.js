import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import axios from "axios";
import authHeaders from './authHeaders';
import "flexboxgrid2";

import "./index.css";
import App from "./App";

(async () => {
  const { Authorization } = await authHeaders(process.env);
  const defaultHeaders = {
    Authorization,
    "X-Requested-With": "XMLHttpRequest",
    Accept: "application/json"
  };

  axios.defaults.headers.common = defaultHeaders;

  ReactDOM.render(
    <BrowserRouter>
      <App />
    </BrowserRouter>,
    document.getElementById("root")
  );
})();
