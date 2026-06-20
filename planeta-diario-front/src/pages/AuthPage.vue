<template>
  <div class="min-h-screen bg-[#E5E5E1] flex items-center justify-center p-4 overflow-hidden relative">
    
    <transition name="slide-down">
      <div v-if="toast.show" 
           class="fixed top-8 left-1/2 -translate-x-1/2 z-50 px-8 py-4 shadow-2xl font-sans font-bold text-sm text-center transition-all border-b-4"
           :class="toast.type === 'error' ? 'bg-[#1C1C1E] text-red-400 border-red-600' : 'bg-[#1C1C1E] text-[#CBA052] border-[#CBA052]'">
        {{ toast.message }}
      </div>
    </transition>

    <div class="relative w-full max-w-[450px] h-[720px] perspective-2000">
      
      <div class="relative w-full h-full transition-transform duration-1000 transform-style-3d cursor-pointer"
           :class="{ 'rotate-y-180': isFlipped }" @click="flipPage(true)">
        
        <div class="absolute inset-0 backface-hidden rounded-r-lg shadow-2xl overflow-hidden border-y-4 border-r-4 border-black/10">
          <img src="../assets/capa-hq.jpg" alt="Daily Planet" class="w-full h-full object-cover" />
          <div class="absolute bottom-0 inset-x-0 bg-gradient-to-t from-black/80 p-8 text-white">
            <h2 class="font-serif text-3xl font-black italic uppercase leading-none tracking-tighter">Winds of Change</h2>
            <p class="font-sans text-sm mt-2 opacity-80">By LOIS LANE | Daily Planet Extra</p>
            <div class="mt-4 inline-block bg-[#CBA052] text-[#1C1C1E] px-4 py-1 text-xs font-bold uppercase tracking-widest">
              Clique para Abrir
            </div>
          </div>
        </div>

        <div class="absolute inset-0 backface-hidden rotate-y-180 bg-[#F9F9F6] p-8 flex flex-col border-y-4 border-l-4 border-black/5 shadow-2xl rounded-l-lg" @click.stop>
          
          <button @click="flipPage(false)" class="absolute top-4 left-4 text-[#1C1C1E]/50 hover:text-[#1C1C1E] transition-colors" title="Voltar para a capa">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
            </svg>
          </button>

          <div class="text-center border-b-2 border-[#1C1C1E] pb-4 mb-6 mt-4">
            <h1 class="font-serif text-4xl font-black uppercase tracking-tighter text-[#1C1C1E]">O Planeta Diário</h1>
            <div class="flex justify-between text-[9px] font-bold uppercase mt-1 px-1">
              <span>Metrópolis</span>
              <span>{{ currentDate }}</span>
              <span>Edição Digital</span>
            </div>
          </div>

          <div class="flex-grow flex flex-col justify-center">
            
            <h3 class="font-serif text-2xl mb-6 text-center italic border-b border-black/10 pb-2">
              {{ isRegister ? 'Novo Recruta' : 'Acesso ao Arquivo' }}
            </h3>
            
            <form v-if="!isRegister" @submit.prevent="handleLogin" class="space-y-5">
              <div>
                <label class="block text-[10px] font-black uppercase tracking-widest mb-1">Identificação (Username)</label>
                <input v-model="loginForm.username" type="text" class="w-full bg-transparent border-b-2 border-[#1C1C1E] py-1 focus:outline-none focus:border-[#0A3161] font-sans" required>
              </div>
              
              <div class="relative">
                <label class="block text-[10px] font-black uppercase tracking-widest mb-1">Código de Segurança (Senha)</label>
                <input v-model="loginForm.password" :type="showLoginPassword ? 'text' : 'password'" class="w-full bg-transparent border-b-2 border-[#1C1C1E] py-1 pr-8 focus:outline-none focus:border-[#0A3161] font-sans" required>
                <button type="button" @click="showLoginPassword = !showLoginPassword" class="absolute right-0 bottom-1 text-black/40 hover:text-[#0A3161]">
                  <svg v-if="!showLoginPassword" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5"><path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" /><path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" /></svg>
                  <svg v-else xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5"><path stroke-linecap="round" stroke-linejoin="round" d="M3.98 8.223A10.477 10.477 0 0 0 1.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.451 10.451 0 0 1 12 4.5c4.756 0 8.773 3.162 10.065 7.498a10.522 10.522 0 0 1-4.293 5.774M6.228 6.228 3 3m3.228 3.228 3.65 3.65m7.894 7.894L21 21m-3.228-3.228-3.65-3.65m0 0a3 3 0 1 0-4.243-4.243m4.242 4.242L9.88 9.88" /></svg>
                </button>
              </div>

              <button class="w-full bg-[#0A3161] text-white py-3 font-serif font-bold text-lg hover:bg-[#0A3161]/90 transition-all shadow-lg active:scale-95">
                Autenticar
              </button>
            </form>

            <form v-else @submit.prevent="handleRegister" class="space-y-4">
              <div class="flex gap-4 mb-2 justify-center border-b border-black/10 pb-3">
                <label class="flex items-center gap-1 text-[10px] font-black uppercase tracking-widest cursor-pointer hover:text-[#CBA052] transition-colors">
                  <input type="radio" v-model="registerForm.tipo" value="COMUM" class="accent-[#CBA052] w-3 h-3">
                  Apenas Leitor
                </label>
                <label class="flex items-center gap-1 text-[10px] font-black uppercase tracking-widest cursor-pointer hover:text-[#0A3161] transition-colors">
                  <input type="radio" v-model="registerForm.tipo" value="CRIADOR_DE_CONTEUDO" class="accent-[#0A3161] w-3 h-3">
                  Criador de Conteúdo
                </label>
              </div>

              <div class="mb-4">
                <label class="block text-[10px] font-black uppercase tracking-widest mb-1">Nome de Apresentação</label>
                <input v-model="registerForm.nome" type="text" placeholder="Ex: Clark Kent" class="w-full bg-transparent border-b border-[#1C1C1E] py-1 focus:outline-none focus:border-[#CBA052] font-serif text-lg italic" required>
              </div>

              <div class="grid grid-cols-2 gap-4">
                <div>
                  <label class="block text-[10px] font-black uppercase tracking-widest mb-1">Username</label>
                  <input v-model="registerForm.username" type="text" class="w-full bg-transparent border-b border-[#1C1C1E] py-1 focus:outline-none focus:border-[#CBA052] font-sans text-sm" required>
                </div>
                <div>
                  <label class="block text-[10px] font-black uppercase tracking-widest mb-1">E-mail</label>
                  <input v-model="registerForm.email" type="email" class="w-full bg-transparent border-b border-[#1C1C1E] py-1 focus:outline-none focus:border-[#CBA052] font-sans text-sm" required>
                </div>
              </div>
              
              <div class="relative">
                <label class="block text-[10px] font-black uppercase tracking-widest mb-1">Senha Segura</label>
                <input v-model="registerForm.senha" :type="showRegPassword ? 'text' : 'password'" class="w-full bg-transparent border-b border-[#1C1C1E] py-1 pr-8 focus:outline-none focus:border-[#CBA052] font-sans text-sm" required>
                <button type="button" @click="showRegPassword = !showRegPassword" class="absolute right-0 bottom-1 text-black/40 hover:text-[#CBA052]">
                  <svg v-if="!showRegPassword" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5"><path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" /><path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" /></svg>
                  <svg v-else xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5"><path stroke-linecap="round" stroke-linejoin="round" d="M3.98 8.223A10.477 10.477 0 0 0 1.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.451 10.451 0 0 1 12 4.5c4.756 0 8.773 3.162 10.065 7.498a10.522 10.522 0 0 1-4.293 5.774M6.228 6.228 3 3m3.228 3.228 3.65 3.65m7.894 7.894L21 21m-3.228-3.228-3.65-3.65m0 0a3 3 0 1 0-4.243-4.243m4.242 4.242L9.88 9.88" /></svg>
                </button>
              </div>

              <div v-if="registerForm.tipo === 'CRIADOR_DE_CONTEUDO'" class="transition-all duration-300">
                <label class="block text-[10px] font-black uppercase tracking-widest mb-1 text-[#0A3161]">Sua Especialidade / Profissão</label>
                <input v-model="registerForm.profissao" type="text" placeholder="Ex: Jornalista, Nutricionista..." class="w-full bg-transparent border-b border-[#0A3161] py-1 focus:outline-none focus:border-[#CBA052] font-sans text-sm italic" required>
              </div>

              <button type="submit" class="w-full bg-[#CBA052] text-[#1C1C1E] py-3 font-serif font-bold text-lg hover:bg-[#CBA052]/90 transition-all shadow-lg active:scale-95 mt-2">
                Solicitar Credencial
              </button>
            </form>

            <p class="mt-6 text-center text-[11px] font-sans text-black/60">
              {{ isRegister ? 'Já possui acesso?' : 'Não possui credenciais?' }}
              <button type="button" @click="toggleMode" class="font-bold text-[#0A3161] hover:text-[#CBA052] underline underline-offset-4 decoration-2">
                {{ isRegister ? 'Voltar ao Login' : 'Solicitar Registro' }}
              </button>
            </p>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, reactive } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/services/api';

const router = useRouter();
const isFlipped = ref(false);
const isRegister = ref(false);

const showLoginPassword = ref(false);
const showRegPassword = ref(false);

const toast = reactive({ show: false, message: '', type: 'success' });
const showToast = (msg, type = 'success') => {
  toast.message = msg;
  toast.type = type;
  toast.show = true;
  setTimeout(() => { toast.show = false; }, 4000);
};

const loginForm = reactive({ username: '', password: '' });
const registerForm = reactive({ 
  nome: '', 
  username: '', 
  email: '', 
  senha: '', 
  tipo: 'COMUM', 
  profissao: '' 
});

const flipPage = (toFlippedState) => {
  isFlipped.value = toFlippedState;
};

const toggleMode = () => {
  isRegister.value = !isRegister.value;
};

const currentDate = computed(() => {
  const options = { day: '2-digit', month: 'long', year: 'numeric' };
  return new Date().toLocaleDateString('pt-BR', options).toUpperCase();
});

const handleLogin = async () => {
  try {
    const response = await api.post('/auth/login', loginForm);
    
    console.log("Resposta real do Java:", response.data);

    const tokenRecibido = response.data.token || response.data.accessToken;

    if (!tokenRecibido) {
      showToast("Erro: O Java não devolveu o token esperado!", "error");
      return;
    }

    localStorage.setItem('token', tokenRecibido);
    router.push('/');
  } catch (error) {
    console.error("Erro no login:", error);
    showToast("Credenciais inválidas. O arquivo confidencial permanece fechado.", "error");
  }
};

const handleRegister = async () => {
  try {
    if (registerForm.tipo === 'COMUM') {
      registerForm.profissao = null;
    }
    
    await api.post('/auth/register', registerForm);
    
    showToast("Credencial criada com sucesso! Faça o login para acessar os arquivos.", "success");
    
    registerForm.nome = '';
    registerForm.username = '';
    registerForm.email = '';
    registerForm.senha = '';
    registerForm.profissao = '';
    isRegister.value = false; 
    
  } catch (error) {
    console.error("Erro no registro:", error);
    showToast("Erro ao solicitar registro. Verifique se preencheu tudo corretamente.", "error");
  }
};
</script>

<style scoped>
.perspective-2000 { perspective: 2000px; }
.transform-style-3d { transform-style: preserve-3d; }
.backface-hidden { backface-visibility: hidden; }
.rotate-y-180 { transform: rotateY(180deg); }
form, input, button { cursor: auto; }

.slide-down-enter-active, .slide-down-leave-active {
  transition: all 0.4s ease;
}
.slide-down-enter-from, .slide-down-leave-to {
  opacity: 0;
  transform: translate(-50%, -20px);
}
</style>