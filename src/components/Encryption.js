// import React from 'react';
// import CryptoJS from 'crypto-js';

// const AES_ALGORITHM = 'AES';

// class Encryption extends React.Component {
//   encryptPassword = (password) => {
//     try {
//       const secretKey = this.generateSecretKey();
//       const encryptedBytes = CryptoJS.AES.encrypt(password, secretKey).toString();
//       return btoa(encryptedBytes);
//     } catch (error) {
//       console.error(error);
//     }
//     return null;
//   };

//   generateSecretKey = () => {
//     const secretKeyString = 'mySecretKey123456789';
//     const secretKeyBytes = CryptoJS.SHA256(secretKeyString).toString(CryptoJS.enc.Utf8).slice(0, 16);
//     return secretKeyBytes;
//   };

//   render() {
//     // Example usage
//     const encryptedPassword = this.encryptPassword('myPassword123');
//     console.log('Encrypted Password:', encryptedPassword);
//     return null;
//   }
// }

// export default Encryption;