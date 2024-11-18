import { useEffect, useRef, useState } from 'react';
import { createPostPost, getImageUrlsPost } from '../api/boardApi';
import useCustomMove from './useCustomMove';
import { useParams } from 'react-router-dom';

const useEditor = () => {
  const modules = {
    toolbar: [
      [{ header: '1' }, { header: '2' }, { font: [] }],
      [{ list: 'ordered' }, { list: 'bullet' }],
      ['bold', 'italic', 'underline', 'strike', 'blockquote'],
      [{ color: [] }, { background: [] }],
      [{ align: [] }],
      ['image'],
      ['clean'],
    ],
  };

  const formats = [
    'header',
    'font',
    'size',
    'bold',
    'italic',
    'underline',
    'strike',
    'blockquote',
    'list',
    'bullet',
    'indent',
    'image',
    'color',
    'background',
    'align',
  ];

  const [content, setContent] = useState('');
  const editorRef = useRef(null);
  const { moveToList } = useCustomMove();
  const { category } = useParams();

  useEffect(() => {
    editorRef.current.focus();
  }, []);

  const handleChange = (value) => {
    setContent(value);
  };

  const handleUpload = (title) => {
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, 'text/html');
    const images = Array.from(doc.querySelectorAll('img'));

    const formData = new FormData();
    const base64Images = [];

    images.forEach((img, index) => {
      if (img.src.startsWith('data:image/')) {
        // Base64 데이터를 추출하여 파일로 변환
        const base64Data = img.src.split(',')[1];
        const byteString = atob(base64Data);
        const arrayBuffer = new Uint8Array(byteString.length);

        for (let i = 0; i < byteString.length; i++) {
          arrayBuffer[i] = byteString.charCodeAt(i);
        }

        const blob = new Blob([arrayBuffer], { type: 'image/png' });
        const file = new File([blob], `image-${index}.png`);
        formData.append('file', file);
      } else {
        // 이미 URL 형식인 경우
        base64Images.push(img.src);
      }
    });
    getImageUrlsPost(formData)
      .then((res) => {
        const uploadedUrls = res.data;
        images.forEach((img, index) => {
          if (img.src.startsWith('data:image/')) {
            img.src = uploadedUrls[index]; // Base64 이미지에 S3 URL 대체
          }
        });

        const updatedHtml = doc.body.innerHTML;

        createPostPost(category, title, updatedHtml)
          .then((res) => {
            console.log(res);
            moveToList();
          })
          .catch(console.error);
      })
      .catch(console.error);
    // console.log(response);
    // const uploadedUrls = response.data.urls;
    //
    // images.forEach((img, index) => {
    //   if (img.src.startsWith('data:image/')) {
    //     img.src = uploadedUrls[index]; // Base64 이미지에 S3 URL 대체
    //   }
    // });
    //
    // const updatedHtml = doc.body.innerHTML;
    // console.log('최종 HTML:', updatedHtml);
    //
    // createPostPost(updatedHtml)
    //   .then((res) => {
    //     console.log(res);
    //     moveToList();
    //   })
    //   .catch(console.error);
  };

  // const createPost = (images, uploadedUrls) => {
  //   images.forEach((img, index) => {
  //     if (img.src.startsWith('data:image/')) {
  //       img.src = uploadedUrls[index]; // Base64 이미지에 S3 URL 대체
  //     }
  //   });
  //
  //   const updatedHtml = doc.body.innerHTML;
  //   console.log('최종 HTML:', updatedHtml);
  //
  //   createPostPost(updatedHtml)
  //     .then((res) => {
  //       console.log(res);
  //       moveToList();
  //     })
  //     .catch(console.error);
  // };

  return { modules, formats, content, handleChange, handleUpload, editorRef };
};

export default useEditor;
