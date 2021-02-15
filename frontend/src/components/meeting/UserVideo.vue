<template>
<div id="video_box" v-if="streamManager" >
	<ov-video :stream-manager="streamManager"/>
	<div id="nameTag"><p>{{ clientData }}</p></div>
</div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data.split("%/%")[0]);
		},

	},
};
</script>

<style>
#nameTag{
	width: 100%;
	font-size: 23px;
	text-align: center;
}
</style>