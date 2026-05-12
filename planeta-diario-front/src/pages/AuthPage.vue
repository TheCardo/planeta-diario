<template>
  <div class="min-h-screen bg-[#E5E5E1] flex items-center justify-center p-4 overflow-hidden">
    <div class="relative w-full max-w-[450px] h-[680px] perspective-2000">
      
      <div 
        class="relative w-full h-full transition-transform duration-1000 transform-style-3d cursor-pointer"
        :class="{ 'rotate-y-180': isFlipped }"
        @click="flipPage(true)" 
      >
        
        <div class="absolute inset-0 backface-hidden rounded-r-lg shadow-2xl overflow-hidden border-y-4 border-r-4 border-black/10">
          <img 
            src="../assets/capa-hq.jpg" 
            alt="Daily Planet Winds of Change"
            class="w-full h-full object-cover"
          />
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
                <input v-model="loginForm.username" type="text" class="w-full bg-transparent border-b-2 border-[#1C1C1E] py-1 focus:outline-none focus:border-[#0A3161] font-sans">
              </div>
              <div>
                <label class="block text-[10px] font-black uppercase tracking-widest mb-1">Código de Segurança (Senha)</label>
                <input v-model="loginForm.password" type="password" class="w-full bg-transparent border-b-2 border-[#1C1C1E] py-1 focus:outline-none focus:border-[#0A3161] font-sans">
              </div>
              <button class="w-full bg-[#0A3161] text-white py-3 font-serif font-bold text-lg hover:bg-[#0A3161]/90 transition-all shadow-lg active:scale-95">
                Autenticar
              </button>
            </form>

            <form v-else @submit.prevent="handleRegister" class="space-y-4">
              <div>
                <label class="block text-[10px] font-black uppercase tracking-widest mb-1">Nome Completo</label>
                <input v-model="registerForm.nome" type="text" class="w-full bg-transparent border-b border-[#1C1C1E] py-1 focus:outline-none focus:border-[#CBA052] font-sans text-sm">
              </div>
              <div>
                <label class="block text-[10px] font-black uppercase tracking-widest mb-1">E-mail</label>
                <input v-model="registerForm.email" type="email" class="w-full bg-transparent border-b border-[#1C1C1E] py-1 focus:outline-none focus:border-[#CBA052] font-sans text-sm">
              </div>
              <div class="grid grid-cols-2 gap-4">
                <div>
                  <label class="block text-[10px] font-black uppercase tracking-widest mb-1">Username</label>
                  <input v-model="registerForm.username" type="text" class="w-full bg-transparent border-b border-[#1C1C1E] py-1 focus:outline-none focus:border-[#CBA052] font-sans text-sm">
                </div>
                <div>
                  <label class="block text-[10px] font-black uppercase tracking-widest mb-1">Senha</label>
                  <input v-model="registerForm.password" type="password" class="w-full bg-transparent border-b border-[#1C1C1E] py-1 focus:outline-none focus:border-[#CBA052] font-sans text-sm">
                </div>
              </div>
              <button class="w-full bg-[#CBA052] text-[#1C1C1E] py-3 font-serif font-bold text-lg hover:bg-[#CBA052]/90 transition-all shadow-lg active:scale-95">
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

const isFlipped = ref(false);
const isRegister = ref(false);

const loginForm = reactive({ username: '', password: '' });
const registerForm = reactive({ nome: '', email: '', username: '', password: '' });

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

const handleLogin = () => {
  console.log("Login Enviado:", loginForm);
};

const handleRegister = () => {
  console.log("Registro Enviado:", registerForm);
};
</script>

<style scoped>
.perspective-2000 { perspective: 2000px; }
.transform-style-3d { transform-style: preserve-3d; }
.backface-hidden { backface-visibility: hidden; }
.rotate-y-180 { transform: rotateY(180deg); }
form, input, button { cursor: auto; }
</style>