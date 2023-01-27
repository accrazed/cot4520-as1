use std::char::MAX;
use std::sync::mpsc::{Receiver, Sender};
use std::sync::{mpsc, Arc, Mutex};
use std::{thread, vec};

const MAX_THREADS: usize = 8;
// const MAX_NUM: usize = 100;
const MAX_NUM: usize = 100000000;

fn main() {
    sieve_sequential(MAX_NUM)
}

fn sieve_sequential(max: usize) {
    let mut primes = vec![true; max + 1];

    for k in 2..max {
        if !primes[k] {
            continue;
        }

        for i in ((2 * k)..max).step_by(k) {
            primes[i] = false;
        }
    }

    for (i, prime) in primes.iter().enumerate() {
        if *prime {
            println!("{}", i)
        }
    }
}

fn sieve_parallel(max: usize) {
    let mut primes = vec![true; max + 1];

    for k in 2..(max as f64).sqrt() as usize {
        if !primes[k] {
            continue;
        }

        let mut threads = vec![];
        for i in 1..MAX_THREADS {
            let chunk_size = MAX_NUM / MAX_THREADS;
            let prime_index = i * chunk_size;
            let last_multiple = (prime_index / k) * k;
            let last_multiple_local: i64 = last_multiple as i64 - prime_index as i64;
            threads.push(thread::spawn(move || {
                mark_non_primes(
                    &primes[prime_index..(prime_index + chunk_size)],
                    last_multiple_local,
                    k,
                )
            }))
        }
        for thread in threads {
            thread.join();
        }
    }

    for (i, prime) in primes.iter().enumerate() {
        if *prime {
            println!("{}", i)
        }
    }
}

fn mark_non_primes(mut primes: &[bool], offset: i64, step: usize) {}
