import React from 'react';
import useCustomMove from '../../hooks/useCustomMove';

function Pagination({ pageInfo }) {
  const { sort, setQueryParams } = useCustomMove();
  return (
    <div className="flex items-center justify-between rounded-b-lg bg-gray-800 px-4 py-3 sm:px-6">
      <div className="hidden sm:flex sm:flex-1 sm:items-center sm:justify-center">
        <div className="bg-gray-700">
          <nav
            className="isolate inline-flex -space-x-px rounded-md shadow-sm"
            aria-label="Pagination"
          >
            {pageInfo.prev ? (
              <button
                onClick={() =>
                  setQueryParams(() => ({
                    page: pageInfo.prevPage,
                    size: pageInfo.size,
                    sort: sort,
                  }))
                }
                className="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
              >
                <span className="sr-only">Previous</span>
                <svg
                  className="size-5"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                  aria-hidden="true"
                  data-slot="icon"
                >
                  <path
                    fillRule="evenodd"
                    d="M11.78 5.22a.75.75 0 0 1 0 1.06L8.06 10l3.72 3.72a.75.75 0 1 1-1.06 1.06l-4.25-4.25a.75.75 0 0 1 0-1.06l4.25-4.25a.75.75 0 0 1 1.06 0Z"
                    clipRule="evenodd"
                  />
                </svg>
              </button>
            ) : (
              <div className="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-500 ring-1 ring-inset ring-gray-300 focus:z-20 focus:outline-offset-0">
                <span className="sr-only">Previous</span>
                <svg
                  className="size-5"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                  aria-hidden="true"
                  data-slot="icon"
                >
                  <path
                    fillRule="evenodd"
                    d="M11.78 5.22a.75.75 0 0 1 0 1.06L8.06 10l3.72 3.72a.75.75 0 1 1-1.06 1.06l-4.25-4.25a.75.75 0 0 1 0-1.06l4.25-4.25a.75.75 0 0 1 1.06 0Z"
                    clipRule="evenodd"
                  />
                </svg>
              </div>
            )}

            {pageInfo.pageNumList && pageInfo.pageNumList.length !== 0 ? (
              pageInfo.pageNumList.map((pageNum) => (
                <button
                  onClick={() =>
                    setQueryParams(() => ({
                      page: pageNum,
                      size: pageInfo.size,
                      sort: sort,
                    }))
                  }
                  className={`relative z-10 inline-flex items-center px-4 py-2 text-sm font-semibold text-white 
                  ${pageInfo.current === pageNum ? 'bg-indigo-700' : ''}
                  hover:bg-indigo-500 focus:z-20 focus-visible:outline
        focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600`}
                >
                  {pageNum}
                </button>
              ))
            ) : (
              <div
                className="relative z-10 inline-flex items-center px-4 py-2 text-sm font-semibold text-white focus:z-20 focus-visible:outline
        focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              >
                1
              </div>
            )}

            {pageInfo.next ? (
              <button
                onClick={() =>
                  setQueryParams(() => ({
                    page: pageInfo.nextPage,
                    size: pageInfo.size,
                    sort: sort,
                  }))
                }
                className="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
              >
                <span className="sr-only">Next</span>
                <svg
                  className="size-5"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                  aria-hidden="true"
                  data-slot="icon"
                >
                  <path
                    fillRule="evenodd"
                    d="M8.22 5.22a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 0 1 0 1.06l-4.25 4.25a.75.75 0 0 1-1.06-1.06L11.94 10 8.22 6.28a.75.75 0 0 1 0-1.06Z"
                    clipRule="evenodd"
                  />
                </svg>
              </button>
            ) : (
              <div className="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-500 ring-1 ring-inset ring-gray-300 focus:z-20 focus:outline-offset-0">
                <span className="sr-only">Next</span>
                <svg
                  className="size-5"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                  aria-hidden="true"
                  data-slot="icon"
                >
                  <path
                    fillRule="evenodd"
                    d="M8.22 5.22a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 0 1 0 1.06l-4.25 4.25a.75.75 0 0 1-1.06-1.06L11.94 10 8.22 6.28a.75.75 0 0 1 0-1.06Z"
                    clipRule="evenodd"
                  />
                </svg>
              </div>
            )}
          </nav>
        </div>
      </div>
    </div>
  );
}

// <div className="flex flex-1 justify-between sm:hidden">
//   <button
//     onClick={() => handlePrevious()}
//     className="relative inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
//   >
//     Previous
//   </button>
//   <button
//     onClick={() => handleNext()}
//     className="relative ml-3 inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
//   >
//     Next
//   </button>
// </div>

// <div>
//   <p className="text-sm text-gray-700">
//     Showing
//     <span className="font-medium">1</span>
//     to
//     <span className="font-medium">10</span>
//     of
//     <span className="font-medium">97</span>
//     results
//   </p>
// </div>

export default Pagination;
