import React from 'react';
import BasicLayout from '../../layouts/BasicLayout';
import PostDetailComponent from '../../components/board/PostDetailComponent';
import { useParams } from 'react-router-dom';

function PostDetailPage() {
  const { id } = useParams();
  return (
    <BasicLayout>
      <PostDetailComponent id={id} />
    </BasicLayout>
  );
}

export default PostDetailPage;