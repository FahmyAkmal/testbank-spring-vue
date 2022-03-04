<template>
  <div class="list row">
    <div class="col-md-6">
      <h4>Accounts List</h4>
      <ul class="list-group">
        <li class="list-group-item"
          :class="{ active: index == currentIndex }"
          v-for="(account, index) in accounts"
          :key="index"
          @click="setActiveAccount(account, index)"
        >
          {{ account.accountType }}
        </li>
      </ul>
    </div>
    <div class="col-md-6">
      <div v-if="currentAccount">
        <h4>Accounts</h4>
        <div>
          <label><strong>Account Type:</strong></label> {{ currentAccount.accountType }}
        </div>
        <div>
          <label><strong>Account Balance:</strong></label> {{ currentAccount.accountBalance }}
        </div>
        <router-link :to="'/accounts/' + currentAccount.id" class="badge badge-warning"> Deposit/Withdraw </router-link>
      </div>
      <div v-else>
        <br />
        <p>Please click on a Account...</p>
      </div>
    </div>
  </div>
</template>
<script>
import AccountDataService from "../services/AccountDataService";
export default {
  name: "accounts-list",
  data() {
    return {
      accounts: [],
      currentAccount: null,
      currentIndex: -1,
      accType: ""
    };
  },
  methods: {
    retrieveAccounts() {
      AccountDataService.getAll()
        .then(response => {
          this.accounts = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    refreshList() {
      this.retrieveAccounts();
      this.currentAccount = null;
      this.currentIndex = -1;
    },
    setActiveAccount(account, index) {
      this.currentAccount = account;
      this.currentIndex = account ? index : -1;
    },
    
  },
  mounted() {
    this.retrieveAccounts();
  }
};
</script>
<style>
.list {
  text-align: left;
  max-width: 750px;
  margin: auto;
}
</style>