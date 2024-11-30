import { useEffect, useRef, useState } from 'react';
import { postPost, getImageUrlsPost, postPut } from '../api/boardApi';
import useCustomMove from './useCustomMove';
import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { clear } from '../slices/editPostSlice';

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
  const postInfo = useSelector((state) => state.editPostSlice);
  const dispatch = useDispatch();

  const [content, setContent] = useState(postInfo.content);
  const [title, setTitle] = useState(postInfo.title);
  const editorRef = useRef(null);
  const { moveToList, moveToDetail } = useCustomMove();
  const { category, id } = useParams();

  useEffect(() => {
    editorRef.current.focus();
  }, []);

  const handleChangeContent = (value) => {
    setContent(value);
  };

  const handleChangeTitle = (event) => {
    setTitle(event.target.value);
  };

  const handleUpload = () => {
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, 'text/html');
    const images = Array.from(doc.querySelectorAll('img'));
    dispatch(clear());

    const formData = new FormData();
    const base64Images = [];

    const targetImages = images.filter((img) =>
      img.src.startsWith('data:image/'),
    );
    if (targetImages.length === 0) {
      if (!id) {
        postPost(category, title, doc.body.innerHTML)
          .then(() => {
            moveToList();
          })
          .catch(console.error);
        return;
      } else {
        postPut(id, title, doc.body.innerHTML)
          .then(() => {
            moveToList();
          })
          .catch(console.error);
        return;
      }
    }
    targetImages.forEach((img, index) => {
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
    });

    getImageUrlsPost(formData)
      .then((res) => {
        const uploadedUrls = res.data;
        console.log(uploadedUrls);
        targetImages.forEach((img, index) => {
          if (img.src.startsWith('data:image/')) {
            img.src = uploadedUrls[index]; // Base64 이미지에 S3 URL 대체
          }
        });

        const updatedHtml = doc.body.innerHTML;

        if (!id) {
          postPost(category, title, updatedHtml)
            .then(() => {
              moveToList();
            })
            .catch(console.error);
        } else {
          postPut(id, title, updatedHtml)
            .then(() => {
              moveToDetail(id);
            })
            .catch(console.error);
        }
      })
      .catch(console.error);
  };

  return {
    modules,
    formats,
    content,
    handleChangeContent,
    handleUpload,
    editorRef,
    title,
    handleChangeTitle,
  };
};

export default useEditor;
