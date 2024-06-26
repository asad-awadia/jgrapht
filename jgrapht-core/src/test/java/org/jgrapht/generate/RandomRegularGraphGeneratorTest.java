/*
 * (C) Copyright 2018-2023, by Emilio Cruciani and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * See the CONTRIBUTORS.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the
 * GNU Lesser General Public License v2.1 or later
 * which is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR LGPL-2.1-or-later
 */

package org.jgrapht.generate;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.util.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link RandomRegularGraphGenerator}.
 * 
 * @author Emilio Cruciani
 */
public class RandomRegularGraphGeneratorTest
{
    private static final long SEED = 5;

    @Test
    public void testNegativeN()
    {
        assertThrows(IllegalArgumentException.class, () -> new RandomRegularGraphGenerator<>(-10, 1));
    }

    @Test
    public void testNegativeD()
    {
        assertThrows(IllegalArgumentException.class, () -> new RandomRegularGraphGenerator<>(10, -1));
    }

    @Test
    public void testDGreaterThanN()
    {
        assertThrows(IllegalArgumentException.class, () -> new RandomRegularGraphGenerator<>(10, 15));
    }

    @Test
    public void testOddDTimesN()
    {
        assertThrows(IllegalArgumentException.class, () -> new RandomRegularGraphGenerator<>(5, 3));
    }

    @Test
    public void testDirectedGraph()
    {
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new RandomRegularGraphGenerator<>(10, 2);
        Graph<Integer, DefaultEdge> g = new DefaultDirectedGraph<>(
            SupplierUtil.createIntegerSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
        assertThrows(IllegalArgumentException.class, () -> gen.generateGraph(g));
    }

    @Test
    public void testPseudograph()
    {
        int n = 100;
        int d = 20;
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new RandomRegularGraphGenerator<>(n, d, SEED);
        Graph<Integer, DefaultEdge> g = new Pseudograph<>(
            SupplierUtil.createIntegerSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
        gen.generateGraph(g);
        for (Integer v : g.vertexSet()) {
            assertEquals(d, g.degreeOf(v));
        }
    }

    @Test
    public void testCompletePseudograph()
    {
        int n = 10;
        int d = n;
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new RandomRegularGraphGenerator<>(n, d, SEED);
        Graph<Integer, DefaultEdge> g = new Pseudograph<>(
            SupplierUtil.createIntegerSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
        gen.generateGraph(g);
        for (Integer v : g.vertexSet()) {
            assertEquals(d, g.degreeOf(v));
        }
    }

    @Test
    public void testSimpleGraph()
    {
        int n = 100;
        int d = 20;
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new RandomRegularGraphGenerator<>(n, d, SEED);
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(
            SupplierUtil.createIntegerSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
        gen.generateGraph(g);
        for (Integer v : g.vertexSet()) {
            assertEquals(d, g.degreeOf(v));
        }
    }

    @Test
    public void testCompleteSimpleGraph()
    {
        int n = 10;
        int d = n - 1;
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new RandomRegularGraphGenerator<>(n, d, SEED);
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(
            SupplierUtil.createIntegerSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
        gen.generateGraph(g);
        for (Integer v : g.vertexSet()) {
            assertEquals(d, g.degreeOf(v));
        }
    }

    @Test
    public void testZeroNodes()
    {
        int n = 0;
        int d = 0;
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new RandomRegularGraphGenerator<>(n, d, SEED);
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(
            SupplierUtil.createIntegerSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
        gen.generateGraph(g);
        assertEquals(0, g.vertexSet().size());
        assertEquals(0, g.edgeSet().size());
    }

    @Test
    public void testZeroDegree()
    {
        int n = 10;
        int d = 0;
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new RandomRegularGraphGenerator<>(n, d, SEED);
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(
            SupplierUtil.createIntegerSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
        gen.generateGraph(g);
        assertEquals(n, g.vertexSet().size());
        assertEquals(0, g.edgeSet().size());
    }
}
