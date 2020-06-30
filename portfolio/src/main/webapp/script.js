// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

function getComments() {
    const languageCode = document.getElementById('language').value;
    const params = new URLSearchParams();
    params.append('languageCode', languageCode);
    
    fetch('/comment?param=' + languageCode )
    .then(response => response.json()).then((comments) => {
    const commentElement = document.getElementById('comment-list');
    comments.forEach((comment) => {
      commentElement.appendChild(createListElement(comment));
    })
  });
}

/** Creates an <li> element containing text. */
function createListElement(comment) {
  const liElement = document.createElement('li');
  liElement.innerText = comment.firstName + ", " + comment.lastName + ": " + comment.comment;
  return liElement;
}