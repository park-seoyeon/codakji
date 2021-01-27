<template>
  <div>
    <div class="modal-overlay" @click.self="$emit('closeModal', false)">
      <div class="modal-window">
        <div class="modal-content">
          <LogIn @closeModal="$emit('closeModal', false)"/>
        </div>
        <footer class="modal-footer">
          <span @click="$emit('closeModal', false)">닫기</span>
        </footer>
      </div>
    </div>
  </div>
</template>

<script>
import LogIn from "@/views/Accounts/LogIn"

export default {
  components: {
    LogIn,
  }
}
</script>

<style>
.modal-overlay {
  display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    z-index: 30;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
}

.modal-window {
  background: #fff;
  border-radius: 4px;
  overflow: hidden;
  min-width: 500px;
}

.modal-content {
  padding: 10px 20px;
}

.modal-footer {
  background: #ccc;
  padding: 5px 20px;
  text-align: right;
}
</style>