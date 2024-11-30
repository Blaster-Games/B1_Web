import React from 'react';
import BasicLayout from '../../layouts/BasicLayout';
import EditPostComponent from '../../components/board/EditPostComponent';

function CreatePostPage() {
  return (
    <BasicLayout>
      <EditPostComponent />
    </BasicLayout>
  );
}

export default CreatePostPage;