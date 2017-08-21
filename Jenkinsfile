node {
  git url: 'https://github.com/jeremypulcifer/keystone.git'
  def mvnHome = tool 'M3'
  sh "${mvnHome}/bin/mvn -B -Dmaven.test.failure.ignore verify"
  
    docker.withRegistry('https://localhost:5000') {
    
        sh "git rev-parse HEAD > .git/commit-id"
        def commit_id = readFile('.git/commit-id').trim()
        println commit_id
    
        stage "build"
        def app = docker.build "keystone"
    
        stage "publish"
        app.push 'master'
        app.push "${commit_id}"

		try {
	        sh 'docker stop keystone'
	        sh 'docker rm keystone'
	    } catch (caughtError) {
	        echo 'Whoops, Already Stopped....'
	    }
	    
        sh 'docker run -d -p 9000:8090 --network mongo --name keystone --link mongo keystone:latest'
    }
}
