import React, { useState } from 'react';
import axios from 'axios';

const EmailForm = () => {
  const [emailData, setEmailData] = useState({
    recipients: '',
    subject: '',
    content: ''
  });

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setEmailData(prevData => ({ ...prevData, [name]: value }));
  };

  const handleRecipientChange = (event) => {
    const { value } = event.target;
    setEmailData(prevData => ({ ...prevData, recipients: value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
  
    const emailDataWithMultipleRecipients = {
      ...emailData,
      recipients: emailData.recipients.split(',').map(recipient => recipient.trim())
    };
  
    axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/send-email`, emailDataWithMultipleRecipients) // 세아
      .then(response => {
        console.log('Email sent:', response.data);
        // 이메일 전송 성공 시 처리 로직 추가
      })
      .catch(error => {
        console.error('Error sending email:', error);
        // 이메일 전송 실패 시 처리 로직 추가
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        name="recipients"
        placeholder="Recipients (comma-separated)"
        value={emailData.recipients}
        onChange={handleRecipientChange}
        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-seahColor sm:text-sm sm:leading-6 px-1.5 mb-2"
      />
      <input
        type="text"
        name="subject"
        placeholder="Subject"
        value={emailData.subject}
        onChange={handleInputChange}
        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-seahColor sm:text-sm sm:leading-6 px-1.5 mb-2"
      />
      <textarea
        name="content"
        placeholder="Content"
        value={emailData.content}
        onChange={handleInputChange}
        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-seahColor sm:text-sm sm:leading-6 px-1.5 mb-2"
      />


      <button type="submit">Send Email</button>
    </form>
  );
};

export default EmailForm;