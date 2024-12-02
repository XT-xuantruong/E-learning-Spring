<template>
  <DefaultLayout>
    <div class="min-h-screen bg-gray-50 py-8 px-4 sm:px-6 lg:px-8">
      <div class="max-w-5xl mx-auto">
        <!-- Header -->
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-gray-900">
            Question/Answer Management
          </h1>
        </div>

        <!-- Excel Import Section -->
        <div class="bg-white rounded-lg shadow-sm mb-8 p-6">
          <div class="mb-6">
            <h2 class="text-lg font-semibold mb-4">
              Import Questions from Excel
            </h2>
            <div class="flex items-center gap-4">
              <input
                type="file"
                ref="fileInput"
                @change="handleFileUpload"
                accept=".xlsx,.xls"
                class="hidden"
              />
              <button
                @click="$refs.fileInput.click()"
                class="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700 transition-colors"
              >
                Select Excel File
              </button>
              <span v-if="selectedFile" class="text-sm text-gray-600">
                {{ selectedFile.name }}
              </span>
            </div>
          </div>

          <!-- Excel Preview section -->
          <div v-if="previewExcel.length" class="mt-6">
            <h3 class="text-md font-medium mb-3">Preview:</h3>
            <div class="max-h-[400px] overflow-y-auto border rounded-lg">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th
                      class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
                    >
                      Quiz
                    </th>
                    <th
                      class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
                    >
                      Question
                    </th>
                    <th
                      class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
                    >
                      Answers
                    </th>
                    <th
                      class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
                    >
                      Correct Answer
                    </th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr v-for="(item, index) in previewExcel" :key="index">
                    <td class="px-6 py-4 whitespace-nowrap">{{ item.quiz }}</td>
                    <td class="px-6 py-4">{{ item.question }}</td>
                    <td class="px-6 py-4">
                      {{
                        Object.values(item.answers)
                          .map((element) => element.answer_text)
                          .join(", ")
                      }}
                    </td>
                    <td class="px-6 py-4">
                      {{ findCorrectAnswer(item.answers) }}
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div class="mt-4 flex justify-end gap-3">
              <button
                @click="confirmImport"
                class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 transition-colors"
              >
                Import Data
              </button>
              <button
                @click="cancelImport"
                class="px-4 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-gray-300 transition-colors"
              >
                Cancel
              </button>
            </div>
          </div>
        </div>

        <!-- Add Question Section -->
        <div class="bg-white rounded-lg shadow-sm mb-8 p-6">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-xl font-semibold text-gray-900">
              Add Question/Answer
            </h2>
            <button
              @click="add"
              class="inline-flex items-center px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white text-sm font-medium rounded-md shadow-sm transition-colors duration-200"
            >
              <font-awesome-icon :icon="['fas', 'plus']" class="mr-2" /> Add
              Question
            </button>
          </div>
        </div>

        <!-- Question Modal -->
        <div
          v-if="isModalOpen"
          class="fixed inset-0 z-50 overflow-y-auto"
          role="dialog"
        >
          <div
            class="flex items-center justify-center min-h-screen px-4 pt-4 pb-20 text-center sm:p-0"
          >
            <!-- Overlay -->
            <div
              class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"
              @click="isModalOpen = false"
            ></div>

            <!-- Modal Content -->
            <div
              class="relative inline-block bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:max-w-lg w-full"
            >
              <div class="px-6 pt-5 pb-4">
                <div class="mb-4">
                  <h3 class="text-lg font-semibold text-gray-900">
                    {{ editingIndex === -1 ? "Add Question" : "Edit Question" }}
                  </h3>
                </div>

                <!-- Quiz Selection -->
                <div class="mb-4">
                  <label class="block text-sm font-medium text-gray-700 mb-2"
                    >Quiz:</label
                  >
                  <select
                    v-model="currentQuiz"
                    class="block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                  >
                    <option
                      v-for="quiz in quizzes"
                      :key="quiz.name"
                      :value="quiz"
                    >
                      {{ quiz.name }}
                    </option>
                  </select>
                </div>

                <!-- Question Input -->
                <div class="mb-4">
                  <label class="block text-sm font-medium text-gray-700 mb-2"
                    >Question:</label
                  >
                  <input
                    v-model="newQuestion"
                    type="text"
                    class="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                  />
                </div>

                <!-- Answers Section -->
                <div class="mb-4">
                  <label class="block text-sm font-medium text-gray-700 mb-2"
                    >Answers:</label
                  >
                  <div class="space-y-3">
                    <div
                      v-for="(answer, index) in newAnswers"
                      :key="index"
                      class="flex gap-2"
                    >
                      <input
                        v-model="newAnswers[index].answer_text"
                        type="text"
                        class="flex-1 px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      />
                      <button
                        @click="removeAnswerField(index)"
                        class="px-3 py-2 bg-red-500 hover:bg-red-600 text-white rounded-md shadow-sm transition-colors duration-200"
                      >
                        <font-awesome-icon :icon="['fas', 'trash']" />
                      </button>
                    </div>
                    <button
                      @click="addAnswerField"
                      class="w-full px-3 py-2 bg-green-500 hover:bg-green-600 text-white rounded-md shadow-sm transition-colors duration-200"
                    >
                      <font-awesome-icon :icon="['fas', 'plus']" /> Add answer
                    </button>
                  </div>
                </div>

                <!-- Correct Answer Selection -->
                <div class="mb-4">
                  <label class="block text-sm font-medium text-gray-700 mb-2"
                    >Correct Answer:</label
                  >
                  <select
                    v-model="newCorrectAnswerIndex"
                    class="block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                  >
                    <option
                      v-for="(answer, index) in newAnswers"
                      :key="index"
                      :value="index"
                    >
                      {{ answer.answer_text }}
                    </option>
                  </select>
                </div>
              </div>

              <!-- Modal Footer -->
              <div class="bg-gray-50 px-6 py-4 flex justify-end gap-3">
                <button
                  v-if="editingIndex !== -1"
                  @click="updateQuestion"
                  class="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white text-sm font-medium rounded-md shadow-sm transition-colors duration-200"
                >
                  Update
                </button>
                <button
                  v-else
                  @click="addQuestion"
                  class="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white text-sm font-medium rounded-md shadow-sm transition-colors duration-200"
                >
                  Add
                </button>
                <button
                  @click="isModalOpen = false"
                  class="px-4 py-2 bg-white border border-gray-300 hover:bg-gray-50 text-gray-700 text-sm font-medium rounded-md shadow-sm transition-colors duration-200"
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Quizzes List -->
        <div class="bg-white rounded-lg shadow-sm p-6">
          <h2 class="text-xl font-semibold text-gray-900 mb-6">Quizzes</h2>
          <div class="space-y-6">
            <div
              v-for="quiz in quizzes"
              :key="quiz.name"
              class="border border-gray-200 rounded-lg p-4"
            >
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-gray-900">
                  {{ quiz.name }}
                </h3>

                <button
                  @click="showQuestions(quiz)"
                  class="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white text-sm font-medium rounded-md shadow-sm transition-colors duration-200"
                >
                  <font-awesome-icon
                    :icon="['fas', selectedQuiz === quiz ? 'eye-slash' : 'eye']"
                  />
                  {{
                    selectedQuiz === quiz ? "Hide Questions" : "View Questions"
                  }}
                </button>
              </div>

              <!-- Questions List -->
              <div
                v-if="selectedQuiz === quiz"
                class="mt-4 max-h-[400px] overflow-y-auto rounded-lg border border-gray-200 bg-gray-50"
              >
                <div class="space-y-4 p-4">
                  <div
                    v-for="(question, index) in quiz.questions"
                    :key="index"
                    class="bg-white rounded-lg p-4 shadow-sm"
                  >
                    <div class="mb-3">
                      <h4 class="text-md font-medium text-gray-900">
                        {{ question.question }}
                      </h4>
                    </div>
                    <ul class="mb-4 space-y-2">
                      <li
                        v-for="(answer, answerIndex) in question.answers"
                        :key="answerIndex"
                        class="flex items-center gap-2 pl-4"
                      >
                        <span
                          :class="{
                            'font-semibold text-green-600': answer.is_correct,
                            'text-gray-700': answer.is_correct,
                          }"
                        >
                          {{ answer.answer_text }}
                        </span>
                        <span
                          v-if="answer.is_correct"
                          class="text-xs bg-green-100 text-green-800 px-2 py-1 rounded-full"
                        >
                          Correct
                        </span>
                      </li>
                    </ul>
                    <div class="flex justify-end gap-2">
                      <button
                        @click="editQuestion(index, quiz)"
                        class="mr-2 text-indigo-600 hover:text-indigo-900 bg-indigo-50 px-3 py-1 rounded-md hover:bg-indigo-100 transition-colors duration-200"
                      >
                        <font-awesome-icon :icon="['fas', 'pen']" />
                      </button>
                      <button
                        @click="deleteQuestion(question.id, quiz)"
                        class="text-red-600 hover:text-red-900 bg-red-50 px-3 py-1 rounded-md hover:bg-red-100 transition-colors duration-200"
                      >
                        <font-awesome-icon :icon="['fas', 'trash']" />
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script setup>
import { ref, computed, onBeforeMount } from "vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import * as XLSX from "xlsx";
import quizServices from "@/services/quizServices";
import questionServices from "@/services/questionServices";
import answerServices from "@/services/answerServices";

const quizzes = ref([]);
const newQuestion = ref("");
const questionID = ref("");
const newAnswers = ref([{ answer_text: "", is_correct: false }]);
const newCorrectAnswerIndex = ref(0);
const editingIndex = ref(-1);
const editingQuiz = ref(null);
const isModalOpen = ref(false);
const selectedQuiz = ref(null);
const fileInput = ref(null);
const selectedFile = ref(null);
const previewData = ref([]);
const previewExcel = ref([]);

// Computed
const currentQuiz = computed({
  get: () => editingQuiz.value || quizzes.value[0],
  set: (quiz) => {
    editingQuiz.value = quiz;
  },
});

// Excel import functions
const handleFileUpload = (event) => {
  const file = event.target.files[0];
  selectedFile.value = file;

  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      const data = new Uint8Array(e.target.result);
      const workbook = XLSX.read(data, { type: "array" });
      const worksheet = workbook.Sheets[workbook.SheetNames[0]];
      const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });

      // Skip header row
      const dataRows = jsonData.slice(1);

      // Process data according to new format
      const formattedData = dataRows
        .map((row) => {
          const quiz = row[0];
          const question = row[1];
          // Get answers from columns C to F (or until empty cell)
          const answers = row
            .slice(2)
            .filter((cell, index) => index < row.length - 3 && cell);
          // Get correct answer index (0-based)
          const correctAnswerIndex = row[row.length - 1];
          answers.forEach((answer, index) => {
            answers[index] = { answer_text: answer, is_correct: false };
            if (answer === correctAnswerIndex) {
              answers[index] = { answer_text: answer, is_correct: true };
            }
          });
          return {
            quiz,
            question,
            answers,
          };
        })
        .filter((item) => item.quiz && item.question); // Filter out empty rows

      previewExcel.value = formattedData;
    };
    reader.readAsArrayBuffer(file);
  }
};

const findCorrectAnswer = (answers) => {
  console.log(answers);
  const correctAnswer = answers.find((answer) => answer.is_correct);
  return correctAnswer ? correctAnswer.answer_text : "";
};

const findQuiz = (name) => {
  const quiz = quizzes.value.find((ele) => ele.name === name);
  return quiz ? quiz.id : "";
};

const confirmImport = async () => {
  const quizGroups = {};

  await Promise.all(
    previewExcel.value.map(async (item) => {
      try {
        const questionRes = await questionServices.create({
          question_text: item.question,
          quiz: findQuiz(item.quiz),
        });

        const questionData = questionRes.data.data;

        await Promise.all(
          item.answers.map(async (answer) => {
            await answerServices.create({
              question: questionData.id,
              answer_text: answer.answer_text,
              is_correct: answer.is_correct,
            });
          })
        );

        if (!quizGroups[item.quiz]) {
          quizGroups[item.quiz] = {
            name: item.quiz,
            questions: [],
          };
        }

        quizGroups[item.quiz].questions.push({
          question: item.question,
          answers: item.answers,
        });
      } catch (error) {
        console.error("Error processing item:", error);
      }
    })
  );

  console.log("Quiz Groups:", quizGroups);

  const importedQuizzes = Object.values(quizGroups);

  importedQuizzes.forEach((importedQuiz) => {
    const existingQuiz = quizzes.value.find(
      (q) => q.name === importedQuiz.name
    );

    if (existingQuiz) {
      existingQuiz.questions.push(...importedQuiz.questions);
    } else {
      quizzes.value.push(importedQuiz);
    }
  });

  console.log(quizzes);
  cancelImport();
};

const cancelImport = () => {
  previewExcel.value = [];
  selectedFile.value = null;
  if (fileInput.value) {
    fileInput.value.value = "";
  }
};

const add = () => {
  clearNewQuestionForm();
  editingIndex.value = -1;
  editingQuiz.value = null;
  isModalOpen.value = true;
};

const addQuestion = async () => {
  if (!newQuestion.value || newAnswers.value.some((answer) => !answer)) {
    alert("Please fill in all fields");
    return;
  }
  await questionServices
    .create({
      question_text: newQuestion.value,
      quiz: currentQuiz.value.id,
    })
    .then((res) => {
      const data = res.data.data;
      newAnswers.value.forEach(async (answer, index) => {
        await answerServices.create({
          question: data.id,
          answer_text: answer.answer_text,
          is_correct: newCorrectAnswerIndex.value == index ? true : false,
        });

        newAnswers.value[index] = {
          answer_text: answer.answer_text,
          is_correct: newCorrectAnswerIndex.value == index ? true : false,
        };
        console.log(newCorrectAnswerIndex);
      });
    });

  if (currentQuiz.value && currentQuiz.value.questions) {
    currentQuiz.value.questions.push({
      question: newQuestion.value,
      answers: [...newAnswers.value],
    });
  } else {
    currentQuiz.value = {
      ...currentQuiz.value,
      questions: [
        {
          question: newQuestion.value,
          answers: [...newAnswers.value],
        },
      ],
    };
  }

  const quizIndex = quizzes.value.findIndex(
    (q) => q.id === currentQuiz.value.id
  );
  console.log("long ", quizzes.value);

  quizzes.value[quizIndex] = { ...currentQuiz.value };

  quizzes.value = [...quizzes.value];
  console.log(quizzes.value);

  clearNewQuestionForm();
  isModalOpen.value = false;
};

const editQuestion = (index, quiz) => {
  editingIndex.value = index;
  editingQuiz.value = quiz;
  questionID.value = quiz.questions[index].id;
  newQuestion.value = quiz.questions[index].question;
  newAnswers.value = [...quiz.questions[index].answers];
  isModalOpen.value = true;
};

const updateQuestion = async () => {
  if (!newQuestion.value || newAnswers.value.some((answer) => !answer)) {
    alert("Please fill in all fields");
    return;
  }
  quizzes;

  await questionServices
    .update({
      id: questionID.value,
      question_text: newQuestion.value,
      quiz: currentQuiz.value.id,
    })
    .then((res) => {
      const data = res.data.data;

      newAnswers.value.forEach(async (answer, index) => {
        if (!answer.id) {
          await answerServices.create({
            question: data.id,
            answer_text: answer.answer_text,
            is_correct: newCorrectAnswerIndex.value == index ? true : false,
          });
        } else {
          await answerServices.update({
            id: answer.id,
            answer_text: answer.answer_text,
            is_correct: newCorrectAnswerIndex.value == index ? true : false,
          });
        }
        newAnswers.value[index] = {
          answer_text: answer.answer_text,
          is_correct: newCorrectAnswerIndex.value == index ? true : false,
        };
      });
    });

  currentQuiz.value.questions[editingIndex.value] = {
    question: newQuestion.value,
    answers: [...newAnswers.value],
  };
  quizzes.value = [...quizzes.value];
  clearNewQuestionForm();
  editingIndex.value = -1;
  editingQuiz.value = null;
  isModalOpen.value = false;
};

const deleteQuestion = async (index, quiz) => {
  if (confirm("Are you sure you want to delete this question?")) {
    await questionServices.delete(index);
    quiz.questions.splice(index, 1);
    quizzes.value = [...quizzes.value];
  }
};

const showQuestions = (quiz) => {
  selectedQuiz.value = selectedQuiz.value === quiz ? null : quiz;
};

const clearNewQuestionForm = () => {
  newQuestion.value = "";
  newAnswers.value = [{ answer_text: "", is_correct: false }];
};

const addAnswerField = () => {
  newAnswers.value.push({ answer_text: "", is_correct: false });
};

const removeAnswerField = (index) => {
  if (newAnswers.value.length > 1) {
    const newAnswersArray = [...newAnswers.value];
    newAnswersArray.splice(index, 1);
    newAnswers.value = newAnswersArray;
  }
};

onBeforeMount(async () => {
  try {
    const [quizzesResponse, questionsResponse, answersResponse] =
      await Promise.all([
        quizServices.gets(),
        questionServices.gets(),
        answerServices.gets(),
      ]);
    const quizData = quizzesResponse.data.data;


    if (answersResponse.status !== 204 && questionsResponse.status !== 204) {
      const questions = [...questionsResponse.data.data];
      const answers = [...answersResponse.data.data];
      answers.forEach((answer) => {
        answer.answer_text = answer.answerText;
        answer.is_correct = answer.correct;
      });

      questions.forEach((question) => {
        question.question = question.questionText;
      });

      quizzes.value = quizData.map((element) => {
        const questionOfQuiz = questions.filter(
          (question) => question.quiz.id === element.id
        );

        questionOfQuiz.forEach((question) => {
          const answerOfQuestion = answers.filter(
            (answer) => answer.question.id === question.id
          );
          question.answers = answerOfQuestion;
        });

        return {
          ...element,
          name: element.title,
          course_id: element.course.id,
          questions: questionOfQuiz,
        };
      });
    } else {
      quizzes.value = quizData.map((element) => {
        return {
          ...element,
          name: element.title,
          course_id: element.course.id,
          questions: [],
        };
      });
    }
  } catch (error) {
    console.error("Error fetching data:", error);
  }
});
</script>
