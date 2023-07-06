import { useState ,useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css';
import ReCAPTCHA from "react-google-recaptcha";


function Login() {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [verified, setVerified] = useState(false);
  const [failedAttempts, setFailedAttempts] = useState(0);
  const [showCaptcha, setShowCaptcha] = useState(false);
  const [captchaValue, setCaptchaValue] = useState("");
  const navigate = useNavigate();


  useEffect(() => {
    if (failedAttempts >= 3) {
      setShowCaptcha(true);
    }
  }, [failedAttempts]);
  const handleCaptchaChange = (value) => {
    setCaptchaValue(value);
    setVerified(true);
  };
  // function onChange(value) {
  //   console.log("Captcha value:", value);
  //   setVerified(true);
  // }

  async function login(event) {
    event.preventDefault();
    if (failedAttempts >= 3) {
      setShowCaptcha(true);
    }
    if (showCaptcha || failedAttempts < 3) {
      if (captchaValue || failedAttempts < 3) {
        try {

          await axios.post("http://localhost:8080/login", {
            username: username,
            password: password,
          }).then((res) => {
            console.log(res.data);

            if (res.data.message === "Username not exits") {
              const num = failedAttempts + 1;
              setFailedAttempts(num);
              alert("Username not exits");
            }
            else if (res.data.message === "Login Success") {

              navigate('/home');
            }
            else {
              const num = failedAttempts + 1;
              setFailedAttempts(num);
              alert("Incorrect Username and Password not match");
            }
          }, fail => {
            console.error(fail);
          });
        }
        catch (err) {

          alert(err);
        }

      }
    }
  }

  return (
    <div>
      <div class="container">
        <div class="row">
          <h2>Login</h2>
          <hr />
        </div>

        <div class="row">
          <div class="col-sm-6">

            <form>
              <div class="form-group">
                <label>Username</label>
                <input type="email" class="form-control" id="username" placeholder="Enter Username"

                  value={username}
                  onChange={(event) => {
                    setUsername(event.target.value);
                  }}

                />

              </div>

              <div class="form-group">
                <label>password</label>
                <input type="password" class="form-control" id="password" placeholder="Enter Password"

                  value={password}
                  onChange={(event) => {
                    setPassword(event.target.value);
                  }}

                />
              </div>
              {showCaptcha && (
                <div>
                  <ReCAPTCHA
                    sitekey="6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI"
                    onChange={handleCaptchaChange}
                  />

                </div>
              )
              }
              <button type="submit" class="btn btn-primary my-3"  onClick={login} >Login</button>

            </form>

          </div>
        </div>
      </div>

    </div>
  );
}

export default Login;