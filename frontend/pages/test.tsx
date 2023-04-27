const Test = () => {
  return <div>test</div>;
};

export default Test;

// import { useEffect, useState } from 'react';

// let memo = 1;
// export default function Test() {
//   console.log('렌더링됨');
//   const [res, setRes] = useState<number>(memo);

//   useEffect(() => {
//     console.log('1123123', memo);
//   }, [memo]);

//   const plus = () => {
//     memo += 1;
//   };
//   const show = () => {
//     setRes(memo);
//   };

//   return (
//     <div>
//       <div onClick={show}>test</div>
//       <div onClick={plus}>{res}</div>
//     </div>
//   );
// }
