var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var babel = require('gulp-babel');
var eslint = require('gulp-eslint');

var src = 'resources';
var dist = 'dist';

var paths = {
    js: src + '/js/*.js'
};

gulp.task('fail-immediately', () => {
    return gulp.src(paths.js)
        .pipe(eslint())
        // format one at time since this stream may fail before it can format them all at the end
        .pipe(eslint.formatEach())
        // failOnError will emit an error (fail) immediately upon the first file that has an error
        .pipe(eslint.failOnError())
        // need to do something before the process exits? Try this:
        .on('error', error => {
            gulpUtil.log('Stream Exiting With Error: ' + error.message);
        });
});

// 자바스크립트 파일을 하나로 합치고 압축한다.
gulp.task('combine-js', function () {
    return gulp.src(paths.js)
        .pipe(eslint({
            rules: {
                'no-bitwise': 0,
                'camelcase': 1,
                'curly': 1,
                'eqeqeq': 0,
                'no-eq-null': 0,
                'guard-for-in': 1,
                'no-empty': 1,
                'no-use-before-define': 0,
                'no-obj-calls': 2,
                'no-unused-vars': 0,
                'new-cap': 1,
                'no-shadow': 0,
                'strict': 2,
                'no-invalid-regexp': 2,
                'comma-dangle': 2,
                'no-undef': 1,
                'no-new': 1,
                'no-extra-semi': 1,
                'no-debugger': 2,
                'no-caller': 1,
                'semi': 1,
                'quotes': 0,
                'no-unreachable': 2
            },

            globals: ['$', 'window', 'Handlebars', 'eg', 'document'],

            envs: ['node']
        }))
        .pipe(eslint.format())
        .pipe(concat('script.js'))
        .pipe(babel({
            presets: ['es2015']
        }))
        .pipe(uglify().on('error', function(e){
            console.log(e);
        }))
        .pipe(gulp.dest(dist + '/js'));
});

'use strict';

// npm install gulp gulp-eslint

/**
 * Simple example of using ESLint and a formatter
 * Note: ESLint does not write to the console itself.
 * Use format or formatEach to print ESLint results.
 * @returns {stream} gulp file stream
 */
gulp.task('basic', () => {
    return gulp.src(paths.js)
    // default: use local linting config
        .pipe(eslint())
        // format ESLint results and print them to the console
        .pipe(eslint.format());
});

/**
 * Inline ESLint configuration
 * @returns {stream} gulp file stream
 */
gulp.task('inline-config', () => {
    return gulp.src(paths.js)
        .pipe(eslint({
            rules: {
                'no-alert': 0,
                'no-bitwise': 0,
                'camelcase': 1,
                'curly': 1,
                'eqeqeq': 0,
                'no-eq-null': 0,
                'guard-for-in': 1,
                'no-empty': 1,
                'no-use-before-define': 0,
                'no-obj-calls': 2,
                'no-unused-vars': 0,
                'new-cap': 1,
                'no-shadow': 0,
                'strict': 2,
                'no-invalid-regexp': 2,
                'comma-dangle': 2,
                'no-undef': 1,
                'no-new': 1,
                'no-extra-semi': 1,
                'no-debugger': 2,
                'no-caller': 1,
                'semi': 1,
                'quotes': 0,
                'no-unreachable': 2
            },

            globals: ['$'],

            envs: ['node']
        }))
        .pipe(eslint.format());
});

/**
 * Load configuration file
 * @returns {stream} gulp file stream
 */
gulp.task('load-config', () => {
    return gulp.src(paths.js)
        .pipe(eslint({
            // Load a specific ESLint config
            configFile: 'config.json'
        }))
        .pipe(eslint.format());
});

/**
 * Shorthand way to load a configuration file
 * @returns {stream} gulp file stream
 */
gulp.task('load-config-shorthand', () => {
    return gulp.src(paths.js)
    // Load a specific ESLint config
        .pipe(eslint('config.json'))
        .pipe(eslint.format());
});

/**
 * The default task will run all above tasks
 */
// gulp.task('default', [
//     'basic',
//     'inline-config',
//     'load-config',
//     'load-config-shorthand'
//
// ], () => {
//     console.log('All tasks completed successfully.');
// });

//기본 task 설정
gulp.task('default', [
    'fail-immediately', 'combine-js' ]);
