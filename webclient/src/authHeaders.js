import axios from 'axios';

// eslint-disable-next-line complexity
export default async function authHeaders() {

  // await axios.post("/login", {
  //   user: "admin",
  //   password: "123"
  // });
  const basicAuth = 'admin:123';
  return {
    Authorization: `Basic ${btoa(basicAuth)}`
  };
}