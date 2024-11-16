
<template>
  <Modal @close="$emit('close')">
    <template #header>
      <h3 class="text-xl font-bold">Manage Questions - {{ quiz.title }}</h3>
    </template>
    <template #body>
      <div class="mb-4">
        <button @click="showAddQuestionModal = true" 
                class="bg-blue-500 text-white px-4 py-2 rounded mr-2">
          Add Question
        </button>
        <input type="file" ref="fileInput" @change="handleFileUpload" 
               class="hidden" accept=".xlsx,.xls"/>
        <button @click="$refs.fileInput.click()" 
                class="bg-green-500 text-white px-4 py-2 rounded">
          Import Questions
        </button>
      </div>

      <div class="space-y-4">
        <div v-for="question in questions" :key="question.id" 
             class="border p-4 rounded">
          <div class="flex justify-between items-start mb-2">
            <h4 class="text-lg font-semibold">{{ question.question_text }}</h4>
            <div>
              <button @click="editQuestion(question)" 
                      class="bg-yellow-500 text-white px-3 py-1 rounded mr-2">
                Edit
              </button>
              <button @click="deleteQuestion(question.id)" 
                      class="bg-red-500 text-white px-3 py-1 rounded">
                Delete
              </button>
            </div>
          </div>

          <!-- Answers -->
          <div class="ml-4 space-y-2">
            <div v-for="answer in question.answers" :key="answer.id" 
                 class="flex justify-between items-center">
              <div class="flex items-center">
                <span class="mr-2">{{ answer.is_correct ? '✓' : '✗' }}</span>
                <span>{{ answer.answer_text }}</span>
              </div>
              <div>
                <button @click="editAnswer(question, answer)" 
                        class="bg-yellow-500 text-white px-2 py-1 rounded mr-2">
                  Edit
                </button>
                <button @click="deleteAnswer(answer.id)" 
                        class="bg-red-500 text-white px-2 py-1 rounded">
                  Delete
                </button>
              </div>
            </div>
            <button @click="addAnswer(question)" 
                    class="text-blue-500 hover:underline">
              + Add Answer
            </button>
          </div>
        </div>
      </div>
    </template>
  </Modal>

  <!-- Add/Edit Question Modal -->
  <Modal v-if="showAddQuestionModal" @close="showAddQuestionModal = false">
    <template #header>
      <h3 class="text-xl font-bold">
        {{ editingQuestion ? 'Edit' : 'Add' }} Question
      </h3>
    </template>
    <template #body>
      <form @submit.prevent="saveQuestion">
        <div class="mb-4">
          <label class="block mb-2">Question Text</label>
          <textarea v-model="questionForm.question_text" 
                    class="border w-full p-2 rounded" 
                    required></textarea>
        </div>
        <button type="submit" 
                class="bg-blue-500 text-white px-4 py-2 rounded">
          Save
        </button>
      </form>
    </template>
  </Modal>

  <!-- Add/Edit Answer Modal -->
  <Modal v-if="showAnswerModal" @close="showAnswerModal = false">
    <template #header>
      <h3 class="text-xl font-bold">
        {{ editingAnswer ? 'Edit' : 'Add' }} Answer
      </h3>
    </template>
    <template #body>
      <form @submit.prevent="saveAnswer">
        <div class="mb-4">
          <label class="block mb-2">Answer Text</label>
          <input v-model="answerForm.answer_text" 
                 class="border w-full p-2 rounded" 
                 required />
        </div>
        <div class="mb-4">
          <label class="flex items-center">
            <input type="checkbox" v-model="answerForm.is_correct" 
                   class="mr-2" />
            Correct Answer
          </label>
        </div>
        <button type="submit" 
                class="bg-blue-500 text-white px-4 py-2 rounded">
          Save
        </button>
      </form>
    </template>
  </Modal>
</template>

<script setup>
import { ref, onMounted, defineProps, defineEmits } from 'vue'
import Modal from '@/components/Modal.vue'
import { read, utils } from 'xlsx'

const props = defineProps({
  quiz: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['close'])

const questions = ref([])
const showAddQuestionModal = ref(false)
const showAnswerModal = ref(false)
const editingQuestion = ref(null)
const editingAnswer = ref(null)
const currentQuestion = ref(null)
const fileInput = ref(null)

const questionForm = ref({
  question_text: ''
})

const answerForm = ref({
  answer_text: '',
  is_correct: false
})

const fetchQuestions = async () => {
  try {
    const response = await fetch(`/api/quizzes/${props.quiz.id}/questions`)
    questions.value = await response.json()
  } catch (error) {
    console.error('Error fetching questions:', error)
  }
}

const saveQuestion = async () => {
  try {
    const url = editingQuestion.value 
      ? `/api/questions/${editingQuestion.value.id}` 
      : `/api/quizzes/${props.quiz.id}/questions`
    const method = editingQuestion.value ? 'PUT' : 'POST'
    
    await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(questionForm.value)
    })
    
    await fetchQuestions()
    showAddQuestionModal.value = false
    editingQuestion.value = null
    questionForm.value = { question_text: '' }
  } catch (error) {
    console.error('Error saving question:', error)
  }
}

const deleteQuestion = async (id) => {
  if (!confirm('Are you sure you want to delete this question?')) return
  
  try {
    await fetch(`/api/questions/${id}`, { method: 'DELETE' })
    await fetchQuestions()
  } catch (error) {
    console.error('Error deleting question:', error)
  }
}

const editQuestion = (question) => {
  editingQuestion.value = question
  questionForm.value = { ...question }
  showAddQuestionModal.value = true
}

const addAnswer = (question) => {
  currentQuestion.value = question
  editingAnswer.value = null
  answerForm.value = { answer_text: '', is_correct: false }
  showAnswerModal.value = true
}

const editAnswer = (question, answer) => {
  currentQuestion.value = question
  editingAnswer.value = answer
  answerForm.value = { ...answer }
  showAnswerModal.value = true
}

const saveAnswer = async () => {
  try {
    const url = editingAnswer.value 
      ? `/api/answers/${editingAnswer.value.id}` 
      : `/api/questions/${currentQuestion.value.id}/answers`
    const method = editingAnswer.value ? 'PUT' : 'POST'
    
    await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(answerForm.value)
    })
    
    await fetchQuestions()
    showAnswerModal.value = false
    editingAnswer.value = null
    currentQuestion.value = null
    answerForm.value = { answer_text: '', is_correct: false }
  } catch (error) {
    console.error('Error saving answer:', error)
  }
}

const deleteAnswer = async (id) => {
  if (!confirm('Are you sure you want to delete this answer?')) return
  
  try {
    await fetch(`/api/answers/${id}`, { method: 'DELETE' })
    await fetchQuestions()
  } catch (error) {
    console.error('Error deleting answer:', error)
  }
}

// Tiếp tục file QuestionModal.vue

const handleFileUpload = async (event) => {
  const file = event.target.files[0]
  if (!file) return

  try {
    const buffer = await file.arrayBuffer()
    const workbook = read(buffer)
    const worksheet = workbook.Sheets[workbook.SheetNames[0]]
    const data = utils.sheet_to_json(worksheet)
    
    // Process questions and answers from Excel
    for (const row of data) {
      // Create question
      const questionResponse = await fetch(`/api/quizzes/${props.quiz.id}/questions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          question_text: row.question_text
        })
      })
      
      const question = await questionResponse.json()
      
      // Create answers for the question
      if (row.answers) {
        const answers = Array.isArray(row.answers) ? row.answers : JSON.parse(row.answers)
        for (const answer of answers) {
          await fetch(`/api/questions/${question.id}/answers`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(answer)
          })
        }
      }
    }
    
    await fetchQuestions()
    event.target.value = null
  } catch (error) {
    console.error('Error processing Excel file:', error)
  }
}

onMounted(fetchQuestions)
</script>




