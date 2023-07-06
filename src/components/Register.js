import React, { useState } from 'react';
import axios from 'axios';
import CryptoJS from 'crypto-js';



function Register() {
  const [firstname, setFirstname] = useState('');
  const [lastname, setLastname] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [dob, setDob] = useState({
    year: '',
    month: '',
    day: '',
  });

  const AES_ALGORITHM = 'AES-CBC-PKCS5Padding';

  function encryptPassword(password) {
    try {
      const secretKey = generateSecretKey();
      const iv = generateInitializationVector();
      const encryptedBytes = CryptoJS.AES.encrypt(password, secretKey, { iv: iv }).toString();
      return encryptedBytes;
    } catch (error) {
      console.error(error);
    }
    return null;
  }
  
  function generateSecretKey() {
    const secretKeyString = 'mySecretKey123456789';
    const secretKeyBytes = CryptoJS.SHA256(secretKeyString).toString(CryptoJS.enc.Hex).substr(0, 32);
    return secretKeyBytes;
  }
  
  function generateInitializationVector() {
    const iv = CryptoJS.lib.WordArray.random(16);
    return iv;
  }

  async function save(event) {
    event.preventDefault();
    try {
      if (!password) {
        throw new Error('Password is not provided');
      }

      const encryptedPassword = encryptPassword(password);
      console.log('Encrypted Password:', encryptedPassword);

      const data = {
        dob: dob.year+"-"+dob.month+"-"+dob.day,
        userName: username,
        password: encryptedPassword,
        email: email,
        firstName: firstname,
        lastName: lastname,
      };

      await axios.post('http://localhost:8080/signup', data);
      alert('Employee Registration Successful');
    } catch (err) {
      alert(err);
    }
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setDob((prevDob) => ({
      ...prevDob,
      [name]: value,
    }));
  };

  return (
    <div>
      <div className="container mt-4">
        <div className="card">
          <h1>Employee Registration</h1>

          <form>
            <div className="form-group">
              <label>First Name</label>
              <input
                type="text"
                className="form-control"
                id="firstname"
                placeholder="First Name"
                value={firstname}
                onChange={(event) => {
                  setFirstname(event.target.value);
                }}
              />
            </div>
            <div className="form-group">
              <label>Last Name</label>
              <input
                type="text"
                className="form-control"
                id="lastname"
                placeholder="Last Name"
                value={lastname}
                onChange={(event) => {
                  setLastname(event.target.value);
                }}
              />
            </div>
            <div className="form-group">
              <label>User Name</label>
              <input
                type="text"
                className="form-control"
                id="username"
                placeholder=" "
                value={username}
                onChange={(event) => {
                  setUsername(event.target.value);
                }}
              />
            </div>

            <div className="form-group">
              <label>Email</label>
              <input
                type="email"
                className="form-control"
                id="email"
                placeholder="Enter Email"
                value={email}
                onChange={(event) => {
                  setEmail(event.target.value);
                }}
              />
            </div>

            <div className="form-group">
              <label>Password</label>
              <input
                type="password"
                className="form-control"
                id="password"
                placeholder="Enter password"
                value={password}
                onChange={(event) => {
                  setPassword(event.target.value);
                }}
              />
            </div>

            <div className="">
              <p className="">Date Of Birth</p>
              <label>
                Day
                <input
                  type="number"
                  name="day"
                  value={dob.day}
                  onChange={handleInputChange}
                  min="1"
                  max="31"
                  required
                />
              </label>
              <label>
                Month:
                <input
                  type="number"
                  name="month"
                  value={dob.month}
                  onChange={handleInputChange}
                  min="1"
                  max="12"
                  required
                />
              </label>
              <label>
                Year:
                <input
                  type="number"
                  name="year"
                  value={dob.year}
                  onChange={handleInputChange}
                  min="1980"
                  max={new Date().getFullYear()}
                  required
                />
              </label>
            </div>

            <button type="submit" className="btn btn-primary mt-4" onClick={save}>
              Save
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Register;
